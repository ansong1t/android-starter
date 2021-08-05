package com.dyippay.data.remotesources.auth

import com.dyippay.data.entities.Result
import com.dyippay.data.entities.session.Session

interface AuthRemoteSource {

    suspend fun login(email: String, password: String): Result<Session>
}
