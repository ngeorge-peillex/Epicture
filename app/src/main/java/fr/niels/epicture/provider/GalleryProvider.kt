package fr.niels.epicture.provider

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import fr.niels.epicture.model.AuthPayload
import fr.niels.epicture.model.Gallery
import fr.niels.epicture.utils.IMGUR_API_URL

class GalleryProvider {
    private val tag = "ImageProvider"

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
}