package com.dyippay.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dyippay.data.entities.session.user.User

@Dao
abstract class UserDao : EntityDao<User>() {

    @Query("SELECT * FROM ${User.USER_TABLE_NAME} LIMIT 1")
    abstract suspend fun getUser(): User?

    @Query("DELETE FROM ${User.USER_TABLE_NAME}")
    abstract suspend fun deleteUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun saveUser(dbUser: User): Long
}
