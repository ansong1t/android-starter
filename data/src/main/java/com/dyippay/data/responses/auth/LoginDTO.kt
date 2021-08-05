package com.dyippay.data.responses.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginDTO(
    @Json(name = "access_token")
    val accessToken: String = "",
    @Json(name = "expires_in")
    val expiresIn: String = "",
    @Json(name = "token_type")
    val tokenType: String = "",
    @Json(name = "user")
    val user: UserDTO = UserDTO()
)
