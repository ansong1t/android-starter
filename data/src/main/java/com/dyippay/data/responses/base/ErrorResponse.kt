package com.dyippay.data.responses.base

import com.dyippay.data.util.HttpException
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse<Error>(
    @Json(name = "errors") val errors: Error,
    @Json(name = "success") val success: Boolean = false,
    @Json(name = "message") val message: String = "",
    @Json(name = "error_code") val errorCode: String = "",
    @Json(name = "http_status") val httpStatus: Int = 500
)

fun <Error> ErrorResponse<Error>?.toException(): Throwable {
    return this?.let {
        HttpException(
            message = message,
            errorCode = errorCode,
            code = httpStatus
        )
    } ?: UnknownError()
}
