package com.dyippay.data.localsources.user

import com.dyippay.data.entities.session.user.User

interface UserLocalSource {
    suspend fun saveUser(user: User): User

    suspend fun getUser(): User

    suspend fun deleteUser()
}
