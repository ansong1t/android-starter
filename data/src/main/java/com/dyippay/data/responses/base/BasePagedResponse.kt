package com.dyippay.data.responses.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasePagedResponse<T>(
    @Json(name = "data") val data: List<T>,
    @Json(name = "meta") val meta: PagingMeta,
    @Json(name = "success") val success: Boolean = false,
    @Json(name = "message") val message: String = "",
    @Json(name = "http_status") val httpStatus: Int = 500
)
