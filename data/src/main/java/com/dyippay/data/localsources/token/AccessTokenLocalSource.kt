package com.dyippay.data.localsources.token

import com.dyippay.data.entities.session.token.AccessToken

interface AccessTokenLocalSource {
    suspend fun getAccessToken(): AccessToken

    suspend fun saveAccessToken(accessToken: AccessToken): AccessToken

    suspend fun deleteToken()
}
