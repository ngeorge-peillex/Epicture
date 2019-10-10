package fr.niels.epicture.model

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

            for (i in 0 until (data.length())) {
                gallery.images.add(Image.Deserializer().deserialize(data.get(i).toString())!!)
            }
            return gallery
        }
    }
}
