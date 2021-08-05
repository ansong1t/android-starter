package com.dyippay.data.repositories

import com.dyippay.data.entities.Result
import com.dyippay.data.entities.session.Session

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Session>

    suspend fun getSession(): Session
}
