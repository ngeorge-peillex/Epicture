package fr.niels.epicture.provider

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import fr.niels.epicture.model.AuthPayload
import fr.niels.epicture.model.User
import fr.niels.epicture.utils.IMGUR_API_URL

class UserProvider() {
    private val tag = "UserProvider"

    val user = User()

    fun get() {
        Fuel.get(IMGUR_API_URL + "3/account/${AuthPayload.username}")
            .responseObject(User.Deserializer()) { _, _, result ->
                when (result) {
                    is Result.Success -> {
                        val (userResult, _) = result
                        user.merge(userResult)
                    }
                    is Result.Failure -> {
                        Log.e(tag, "Invalid request: $result")
                    }
                }
            }
    }
}