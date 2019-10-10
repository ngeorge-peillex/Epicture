package fr.niels.epicture.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class Image : Observable() {
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
            setChangedAndNotify("link")
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

    fun merge(other: Image?) {
        if (other == null)
            return
        this.apply {
            title = other.title;
            type = other.type;
            link = other.link;
            favorite = other.favorite
        }
    }

    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }

    @Throws(IOException::class)
    private fun drawableFromUrl(url: String): Drawable {
        val x: Bitmap
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val input = connection.getInputStream()
        x = BitmapFactory.decodeStream(input)
        return BitmapDrawable(Resources.getSystem(), x)
    }

    class Deserializer : ResponseDeserializable<Image> {
        override fun deserialize(content: String): Image? {
            val image: Image = Gson().fromJson(content, Image::class.java)
            image.content = image.drawableFromUrl(image.link)
            return image
        }
    }
}
