package com.dyippay.data.entities.session.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dyippay.data.entities.DyippayEntity
import org.threeten.bp.Instant

@Entity(tableName = User.USER_TABLE_NAME)
data class User(
    @PrimaryKey
    override val id: Long = 0,
    @ColumnInfo(name = "full_name")
    var fullName: String? = "",
    @ColumnInfo(name = "first_name")
    var firstName: String? = "",
    @ColumnInfo(name = "last_name")
    var lastName: String? = "",
    var email: String? = "",
    @ColumnInfo(name = "avatar_permanent_url")
    var avatarPermanentUrl: String? = "",
    @ColumnInfo(name = "avatar_permanent_thumb_url")
    var avatarPermanentThumbUrl: String? = "",
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String? = "",
    @ColumnInfo(name = "email_verified")
    var emailVerified: Boolean = false,
    @ColumnInfo(name = "phone_number_verified")
    var phoneNumberVerified: Boolean = false,
    var verified: Boolean = false,
    @ColumnInfo(name = "birthdate") var birthDate: String = "",
    @ColumnInfo(name = "created_at")
    var createdAt: Instant = Instant.now(),
    @ColumnInfo(name = "updated_at")
    var updatedAt: Instant = Instant.now()
) : DyippayEntity {

    companion object {
        const val USER_TABLE_NAME = "user"
        const val EMPTY_USER_ID = "empty"

        /**
         * Returns an empty user.
         */
        fun empty(): User {
            return User(
                fullName = EMPTY_USER_ID
            )
        }
    }

    fun isEmptyUser(): Boolean {
        return fullName == EMPTY_USER_ID
    }
}
