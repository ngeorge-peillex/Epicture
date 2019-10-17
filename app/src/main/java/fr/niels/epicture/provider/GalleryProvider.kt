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

    fun getTrends(type: String) {
        Fuel.get(IMGUR_API_URL + "3/gallery/hot/top/" + type)
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

    fun getSearch(type: String) {
        Fuel.get(IMGUR_API_URL + "3/gallery/r/" + type)
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

    fun getFilter(type: String) {
        Fuel.get(IMGUR_API_URL + "3/gallery/r/" + type)
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

    fun upload(imageUri: InputStream) {
        Fuel.upload(IMGUR_API_URL + "3/image")
            .add(
                BlobDataPart(imageUri, name = "image")
            )
            .response { result ->
                when (result) {
                    is Result.Success -> {
                        get()
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                    }
                }
            }
    }
}