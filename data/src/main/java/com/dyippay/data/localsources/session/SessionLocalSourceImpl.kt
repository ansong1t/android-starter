package com.dyippay.data.localsources.session

import com.dyippay.data.entities.session.Session
import com.dyippay.data.localsources.token.AccessTokenLocalSource
import com.dyippay.data.localsources.user.UserLocalSource
import javax.inject.Inject

class SessionLocalSourceImpl @Inject constructor(
    private val userLocalSource: UserLocalSource,
    private val accessTokenLocalSource: AccessTokenLocalSource
) : SessionLocalSource {

    var session: Session? = null

    override suspend fun getSession(): Session {
        return if (session == null) {
            getSessionFromDb().let {
                this.session = it
                it
            }
        } else this.session!!
    }

    override suspend fun saveSession(session: Session): Session {

        val user = userLocalSource.saveUser(session.user)
        val accessToken = accessTokenLocalSource.saveAccessToken(session.accessToken)
        val newSession = Session(
            user = user,
            accessToken = accessToken
        )

        this.session = newSession
        return newSession
    }

    override suspend fun getUserToken(): String {
        return accessTokenLocalSource.getAccessToken().bearerToken
    }

    private suspend fun getSessionFromDb(): Session {
        val user = userLocalSource.getUser()
        val accessToken = accessTokenLocalSource.getAccessToken()

        return Session(user, accessToken)
    }

    override suspend fun clearSession() {
        userLocalSource.deleteUser()
        accessTokenLocalSource.deleteToken()
        this.session = null
    }
}
