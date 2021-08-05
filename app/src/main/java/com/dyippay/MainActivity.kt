package com.dyippay

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.dyippay.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private var binding: ActivityMainBinding? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.navHostContainer.applySystemWindowInsetsToPadding(top = true)
        binding!!.bottomNavView.applySystemWindowInsetsToPadding(bottom = true)
        binding!!.mainRoot.setEdgeToEdgeSystemUiFlags(true)
        initBottomNav()
        startService(Intent(this, PreviousTimeVisitedService::class.java))
    }

    private fun initBottomNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment

        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)

        binding!!.bottomNavView.setupWithNavController(navController)
    }

    private fun showBottomNav(show: Boolean) {
        binding?.bottomNavView?.run {
            if (show && visibility == View.VISIBLE || !show && visibility == View.GONE) return

            val transition: Transition = Slide(Gravity.BOTTOM)
            transition.duration =
                resources.getInteger(R.integer.bottom_nav_hide_show_duration).toLong()
            transition.addTarget(this)

            TransitionManager.beginDelayedTransition(binding?.root as ViewGroup, transition)
            visibility = if (show) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PreviousTimeVisitedService::class.java))
        super.onDestroy()
        binding = null
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        // BottomNav visibility
        when (destination.id) {
            in NO_BOTTOM_NAV_IDS ->
                showBottomNav(false)
            else -> showBottomNav(true)
        }
    }

    companion object {
        private val NO_BOTTOM_NAV_IDS = arrayOf(
            R.id.songDetailsFragment
        )
    }
}
