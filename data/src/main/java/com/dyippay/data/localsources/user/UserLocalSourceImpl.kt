package com.dyippay.data.localsources.user

import com.dyippay.data.daos.UserDao
import com.dyippay.data.entities.session.user.User
import com.dyippay.util.Logger
import javax.inject.Inject

class UserLocalSourceImpl @Inject constructor(
    private val userDao: UserDao,
    private val logger: Logger
) : UserLocalSource {

    override suspend fun saveUser(user: User): User {
        userDao.deleteUsers()
        val newId = userDao.saveUser(user)
        logger.d("save user $newId")
        return user
    }

    override suspend fun getUser(): User {
        return try {
            userDao.getUser() ?: User.empty()
        } catch (e: Exception) {
            User.empty()
        }
    }

    override suspend fun deleteUser() {
        return userDao.deleteUsers()
    }
}
