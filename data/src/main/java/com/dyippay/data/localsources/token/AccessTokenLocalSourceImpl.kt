package com.dyippay.data.localsources.token

import com.dyippay.data.daos.TokenDao
import com.dyippay.data.entities.session.token.AccessToken
import javax.inject.Inject

class AccessTokenLocalSourceImpl @Inject constructor(
    private val tokenDao: TokenDao
) : AccessTokenLocalSource {

    override suspend fun getAccessToken(): AccessToken {
        return try {
            tokenDao.getToken() ?: AccessToken()
        } catch (e: Exception) {
            AccessToken()
        }
    }

    override suspend fun saveAccessToken(accessToken: AccessToken): AccessToken {
        tokenDao.logoutToken()
        tokenDao.saveToken(accessToken)
        return accessToken
    }

    override suspend fun deleteToken() {
        return tokenDao.logoutToken()
    }
}
