package com.dyippay.data.remotesources.auth

import com.dyippay.data.entities.ErrorResult
import com.dyippay.data.entities.Result
import com.dyippay.data.entities.session.Session
import com.dyippay.data.entities.session.token.AccessToken
import com.dyippay.data.responses.auth.toData
import com.dyippay.data.responses.base.Nothing
import com.dyippay.data.responses.base.toException
import com.dyippay.data.services.AuthService
import com.dyippay.data.util.convertErrorResponse
import com.dyippay.extensions.toResult
import javax.inject.Inject

class AuthRemoteSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteSource {

    override suspend fun login(email: String, password: String): Result<Session> {
        return try {
            authService.login(email, password).toResult(
                mapper = {
                with(it.data) {
                    Session(
                        user = it.data.user.toData(),
                        accessToken = AccessToken(
                            token = accessToken,
                            tokenType = tokenType,
                            expiresIn = expiresIn
                        )
                    )
                }
            },
            errorMapper = {
                it.convertErrorResponse<Nothing>().toException()
            })
        } catch (e: Exception) {
            ErrorResult(e)
        }
    }
}
