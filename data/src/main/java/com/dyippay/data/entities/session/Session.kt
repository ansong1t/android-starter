package com.dyippay.data.entities.session

import com.dyippay.data.entities.session.token.AccessToken
import com.dyippay.data.entities.session.user.User

data class Session(
    var user: User,
    var accessToken: AccessToken
)
