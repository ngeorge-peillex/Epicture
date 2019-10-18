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

    fun get() {
        Fuel.get(IMGUR_API_URL + "3/account/${AuthPayload.username}/images/0")
            .responseObject(Gallery.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val (galleryResult, _) = result
                        gallery.merge(galleryResult)
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                    }
                }
            }
    }

    fun getTrends(period: String) {
        Fuel.get(IMGUR_API_URL + "3/gallery/hot/top/0/" + period)
            .responseObject(Gallery.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val (galleryResult, _) = result
                        gallery.merge(galleryResult)
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                    }
                }
            }
    }

    fun searchGallery(period: String, search: String) {
        Fuel.get(IMGUR_API_URL + "3/gallery/search/top/$period/0?q=$search")
            .responseObject(Gallery.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val (galleryResult, _) = result
                        gallery.merge(galleryResult)
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                    }
                }
            }
    }
}