package fr.niels.epicture.provider

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.BlobDataPart
import com.github.kittinunf.result.Result
import fr.niels.epicture.utils.IMGUR_API_URL
import java.io.InputStream

class ImageProvider {
    private val tag = "ImageProvider"

    fun upload(imageUri: InputStream) {
        Fuel.upload(IMGUR_API_URL + "3/image")
            .add(
                BlobDataPart(imageUri, name = "image")
            )
            .response { result ->
                if (result is Result.Failure) {
                    Log.e(tag, "Invalid request: $result")
                }
            }
    }

    fun favorite(imageHash: String) {
        Fuel.upload(IMGUR_API_URL + "3/image/$imageHash/favorite")
            .response { result ->
                Log.e(tag, "Request: $result")
                if (result is Result.Failure) {
                    Log.e(tag, "Invalid request: $result")
                }
            }
    }

}
