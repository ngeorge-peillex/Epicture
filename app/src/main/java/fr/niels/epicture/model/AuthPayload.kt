package fr.niels.epicture.model

import com.github.kittinunf.fuel.core.Request

object AuthPayload {
    // Bearer JWT
    var token: String = ""

    var expiresIn: Long = 0

    var id: String = ""

    var username: String = ""

    fun interceptor() = { next: (Request) -> Request ->
        { req: Request ->
            req.header(mapOf("Authorization" to "Bearer ${this.token}"))
            next(req)
        }
    }
}
