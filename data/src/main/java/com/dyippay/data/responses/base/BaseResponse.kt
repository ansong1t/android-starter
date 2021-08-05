package com.dyippay.data.responses.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "data") val data: T,
    @Json(name = "success") val success: Boolean = false,
    @Json(name = "message") val message: String = "",
    @Json(name = "error_code") val errorCode: String = "",
    @Json(name = "http_status") val httpStatus: Int = 500
)
