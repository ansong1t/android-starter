package com.dyippay.data.services

import com.dyippay.data.responses.auth.LoginDTO
import com.dyippay.data.responses.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<BaseResponse<LoginDTO>>

    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all")
    suspend fun getItems(): Response<BaseResponse<LoginDTO>>
}
