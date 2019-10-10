package fr.niels.epicture.model

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject
import java.util.*

class User : Observable() {
    @SerializedName("url")
    var username: String = ""
        set(value) {
            field = value
            setChangedAndNotify("username")
        }

    var bio: String = ""
        set(value) {
            field = value
            setChangedAndNotify("bio")
        }

    fun merge(other: User?) {
        if (other == null)
            return
        this.apply { username = other.username; bio = other.bio ?: ""; }
    }

    private fun setChangedAndNotify(field: Any) {
        setChanged()
        notifyObservers(field)
    }

    class Deserializer : ResponseDeserializable<User> {
        override fun deserialize(content: String): User? {
            val data = JSONObject(content).get("data").toString()
            return Gson().fromJson(data, User::class.java)
        }
    }
}
