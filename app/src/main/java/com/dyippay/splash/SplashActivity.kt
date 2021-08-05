package com.dyippay.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.dyippay.MainActivity
import com.dyippay.MainActivity.Companion.EXTRA_START_SCREEN
import com.dyippay.MainActivity.Companion.START_LOGIN
import com.dyippay.MainActivity.Companion.START_MAIN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navState.observe(this) {
            it.getContentIfNotHandled()?.let {
                MainActivity.openActivity(this, Bundle().apply {
                    putString(
                        EXTRA_START_SCREEN,
                        if (it is SplashState.UserIsLoggedIn) START_MAIN
                        else START_LOGIN
                    )
                })
                finishAffinity()
            }
        }
    }
}
