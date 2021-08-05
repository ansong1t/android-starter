package com.dyippay.data.repositories

import com.dyippay.data.entities.Result
import com.dyippay.data.entities.Success
import com.dyippay.data.entities.session.Session
import com.dyippay.data.localsources.session.SessionLocalSource
import com.dyippay.data.remotesources.auth.AuthRemoteSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteSource: AuthRemoteSource,
    private val sessionLocalSource: SessionLocalSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Session> {
        val result = authRemoteSource.login(email, password)
        if (result is Success) {
            sessionLocalSource.saveSession(result.data)
        }
        return result
    }

    override suspend fun getSession(): Session {
        return sessionLocalSource.getSession()
    }
}
