package com.dyippay.splash

sealed class SplashState {
    /**
     * User has logged in.
     */
    object UserIsLoggedIn : SplashState()

    /**
     * User is not logged in, will show login.
     */
    object UserIsNotLoggedIn : SplashState()
}
