package com.dyippay.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dyippay.data.entities.session.token.AccessToken

@Dao
abstract class TokenDao : EntityDao<AccessToken>() {

    @Query("SELECT * FROM ${AccessToken.TOKEN_TABLE_NAME} LIMIT 1")
    abstract suspend fun getToken(): AccessToken?

    @Query("DELETE FROM ${AccessToken.TOKEN_TABLE_NAME}")
    abstract fun logoutToken()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun saveToken(tokenDb: AccessToken): Long
}
