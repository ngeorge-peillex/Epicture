package fr.niels.epicture.provider

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.BlobDataPart
import com.github.kittinunf.result.Result
import fr.niels.epicture.model.AuthPayload
import fr.niels.epicture.model.Gallery
import fr.niels.epicture.utils.IMGUR_API_URL
import java.io.InputStream

class GalleryProvider {
    private val tag = "GalleryProvider"

    var gallery = Gallery()

    var page: Int = 0

    var isInAsyncTask: Boolean = false

    fun get(nextPage: Boolean = false) {
        isInAsyncTask = true
        page = if (nextPage) page + 1 else 0
        Fuel.get(IMGUR_API_URL + "3/account/${AuthPayload.username}/images/$page")
            .responseObject(Gallery.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        mergeGalleryResult(result.component1())
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                        isInAsyncTask = false
                    }
                }
            }
    }

    fun getTrends(period: String, nextPage: Boolean = false) {
        isInAsyncTask = true
        page = if (nextPage) page + 1 else 0
        Fuel.get(IMGUR_API_URL + "3/gallery/hot/top/$page/" + period)
            .responseObject(Gallery.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        mergeGalleryResult(result.component1())
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                        isInAsyncTask = false
                    }
                }
            }
    }

    fun searchGallery(period: String, search: String, nextPage: Boolean = false) {
        isInAsyncTask = true
        page = if (nextPage) page + 1 else 0
        Fuel.get(IMGUR_API_URL + "3/gallery/search/top/$period/$page?q=$search")
            .responseObject(Gallery.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        mergeGalleryResult(result.component1())
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                        isInAsyncTask = false
                    }
                }
            }
    }

    private fun mergeGalleryResult(galleryResult: Gallery?) {
        if (galleryResult == null)
            return

        if (page == 0) {
            gallery.merge(galleryResult)
        } else {
            gallery.images.addAll(galleryResult.images)
            gallery.setChangedAndNotify("images+${galleryResult.images.size}")
        }
        isInAsyncTask = false
    }
}