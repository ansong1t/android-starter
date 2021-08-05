package com.dyippay.data.util

import com.dyippay.data.responses.base.BaseResponse
import com.dyippay.data.responses.base.ErrorResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.ResponseBody
import retrofit2.Response

inline fun <reified T> ResponseBody.convert(): T? {
    val moshi = Moshi.Builder().build()
    val jsonAdapter = moshi.adapter(T::class.java)

    return jsonAdapter.fromJson(string())
}

inline fun <reified T> Response<T>.result(
    onSuccess: T.() -> Unit,
    onError: T.() -> Unit
) {
    body()?.let {
        onSuccess.invoke(it)
    }

    errorBody()?.convert<T>()?.let {
        onError.invoke(it)
    }
}

inline fun <reified T> ResponseBody.convertBaseResponse(): BaseResponse<T>? {
    val moshi = Moshi.Builder().build()
    val type =
        Types.newParameterizedType(BaseResponse::class.java, T::class.java)
    val jsonAdapter = moshi.adapter<BaseResponse<T>>(type)

    return jsonAdapter.fromJson(string())
}

inline fun <reified T> ResponseBody.convertErrorResponse(): ErrorResponse<T>? {
    val moshi = Moshi.Builder().build()
    val type =
        Types.newParameterizedType(ErrorResponse::class.java, T::class.java)
    val jsonAdapter = moshi.adapter<ErrorResponse<T>>(type)

    return jsonAdapter.fromJson(string())
}
