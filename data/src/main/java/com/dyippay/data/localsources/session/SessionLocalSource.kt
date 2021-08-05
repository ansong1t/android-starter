package com.dyippay.data.localsources.session

import com.dyippay.data.entities.session.Session

interface SessionLocalSource {
    suspend fun getSession(): Session

    suspend fun saveSession(session: Session): Session

    suspend fun getUserToken(): String

    suspend fun clearSession()
}
