package com.dyippay.data.entities.session

import com.dyippay.data.entities.session.token.AccessToken
import com.dyippay.data.entities.session.user.User

data class Session(
    val user: User,
    val accessToken: AccessToken
)
