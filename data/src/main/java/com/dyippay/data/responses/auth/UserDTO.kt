package com.dyippay.data.responses.auth

import com.dyippay.data.entities.session.user.User
import com.dyippay.data.util.fromIsoDateTimeToInstant
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDTO(
    @Json(name = "code")
    val code: String? = null,
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "email")
    val email: String = "",
    @Json(name = "email_verified_at")
    val emailVerifiedAt: String = "",
    @Json(name = "id")
    val id: Long = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "phone_number")
    val phoneNumber: String = "",
    @Json(name = "phone_number_verified_at")
    val phoneNumberVerifiedAt: String = "",
    @Json(name = "updated_at")
    val updatedAt: String = ""
)

fun UserDTO.toData(): User {
    return User(
        createdAt = createdAt.fromIsoDateTimeToInstant(),
        email = email,
        emailVerified = emailVerifiedAt.isNotEmpty(),
        id = id,
        fullName = name,
        phoneNumber = phoneNumber,
        phoneNumberVerified = phoneNumberVerifiedAt.isNotEmpty(),
        updatedAt = updatedAt.fromIsoDateTimeToInstant()
    )
}
