package com.dyippay.data.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    val resultCount: Int = 0,
    val results: T? = null
)
