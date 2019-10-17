package fr.niels.epicture.model

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Gallery : Observable() {
    var images: MutableList<Image> = mutableListOf()
        set(value) {
            field = value
            setChangedAndNotify("images")
        }

    fun merge(other: Gallery?) {
        if (other == null)
            return
        this.apply {
            images = other.images
        }
    }

    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }

    class Deserializer : ResponseDeserializable<Gallery> {
        override fun deserialize(content: String): Gallery? {
            var gallery = Gallery()
            val data: JSONArray = JSONObject(content).getJSONArray("data")

            Log.e("debugGallery", "images len : ${data.length()}")
            for (i in 0 until (data.length())) {
                Log.e("debugGallery", "image parsed")
                val tmp = Image.Deserializer().deserialize(data.get(i).toString())
                if (tmp != null) {
                    Log.e("debugGallery", "image pushed")
                    gallery.images.add(tmp)
                }
            }
            return gallery
        }
    }
}
