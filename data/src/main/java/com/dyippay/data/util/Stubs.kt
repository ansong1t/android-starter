package com.dyippay.data.util

import com.dyippay.data.entities.session.Session
import com.dyippay.data.entities.session.token.AccessToken
import com.dyippay.data.entities.session.user.User

object Stubs {

    val USER_LOGGED_IN = User(
        id = 123123123,
        fullName = "Jose Mari Chan",
        firstName = "Jose Mari",
        lastName = "Chan",
        email = "josemarichan@gmail.com",
        avatarPermanentThumbUrl = "http://www.google.com/",
        avatarPermanentUrl = "http://www.google.com/",
        phoneNumber = "+639435643214",
        emailVerified = true,
        phoneNumberVerified = true,
        verified = true
    )

    val VALID_ACCESS_TOKEN = AccessToken(token = "sampleToken")

    val SESSION_LOGGED_IN = Session(USER_LOGGED_IN, VALID_ACCESS_TOKEN)

    val SESSION_NOT_LOGGED_IN = Session(User(), AccessToken())
}
