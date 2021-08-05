package com.dyippay.data.entities.session.token

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dyippay.data.entities.DyippayEntity

@Entity(tableName = AccessToken.TOKEN_TABLE_NAME)
data class AccessToken(
    @ColumnInfo(name = "id") override val id: Long = 0,
    @PrimaryKey
    var token: String = "",
    var refresh: String = "",
    @ColumnInfo(name = "token_type")
    var tokenType: String = "",
    @ColumnInfo(name = "expires_in")
    var expiresIn: String = ""
) : DyippayEntity {

    companion object {
        const val TOKEN_TABLE_NAME = "token"
    }

    constructor() : this(0, "", "", "", "")

    val bearerToken: String get() = "Bearer $token"
}
