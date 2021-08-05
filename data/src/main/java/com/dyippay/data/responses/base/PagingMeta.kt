package com.dyippay.data.responses.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagingMeta(
    @Json(name = "current_page") val currentPage: Int,
    val from: Int? = 0,
    @Json(name = "last_page") val lastPage: Int,
    val path: String,
    @Json(name = "per_page") val perPage: Int,
    val to: Int,
    val total: Int
)
