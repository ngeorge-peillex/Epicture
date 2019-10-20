package fr.niels.epicture.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class Image : Observable() {
    var id: String = ""

    var title: String = ""
        set(value) {
            field = value
            setChangedAndNotify("title")
        }

    var type: String = ""
        set(value) {
            field = value
            setChangedAndNotify("type")
        }

    var link: String = ""
        set(value) {
            field = value
            content = drawableFromUrl(value)
        }

    var content: Drawable = GradientDrawable()
        set(value) {
            field = value
            setChangedAndNotify("content")
        }

    var favorite: Boolean = false
        set(value) {
            field = value
            setChangedAndNotify("favorite")
        }

    @SerializedName("account_url")
    var owner: String = ""
        set(value) {
            field = value
            setChangedAndNotify("owner")
        }

    fun merge(other: Image?) {
        if (other == null)
            return
        this.apply {
            title = other.title
            type = other.type
            link = other.link
            favorite = other.favorite
            owner = other.owner
        }
    }

    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }

    @Throws(IOException::class)
    private fun drawableFromUrl(url: String): Drawable {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.inputStream

        var x: Bitmap = BitmapFactory.decodeStream(input)

        return BitmapDrawable(Resources.getSystem(), x)
    }

    class Deserializer : ResponseDeserializable<Image> {
        override fun deserialize(content: String): Image? {
            val imageLink: String = getImageLink(content)
            if (imageLink.isEmpty())
                return null

            val image: Image = Gson().fromJson(content, Image::class.java)
            image.link = imageLink
            image.content = image.drawableFromUrl(image.link)
            return image
        }

        private fun getImageLink(content: String): String {
            if (!JSONObject(content).has("images")) {
                if (!isValidType(JSONObject(content)))
                    return ""
                return JSONObject(content).getString("link")
            }

            val images: JSONArray = JSONObject(content).getJSONArray("images")
            if (images.length() != 1)
                return ""

            val firstImage = images.getJSONObject(0)
            if (!isValidType(firstImage))
                return ""

            return firstImage.getString("link")
        }

        private fun isValidType(image: JSONObject): Boolean {
            val type: String = image.getString("type")

            return type.startsWith("image/") &&
                    type != "image/gif" &&
                    !image.getBoolean("animated") &&
                    !image.getBoolean("has_sound")
        }
    }
}
