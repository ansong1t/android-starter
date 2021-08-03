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
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.dyippay.common.navigation.BottomNavigationHelper
import com.dyippay.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navigationHelper: BottomNavigationHelper
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.navHostContainer.applySystemWindowInsetsToPadding(top = true)
        binding!!.bottomNavView.applySystemWindowInsetsToPadding(bottom = true)
        binding!!.mainRoot.setEdgeToEdgeSystemUiFlags(true)
        initHelper()
        if (savedInstanceState == null) setupBottomNavigationBar()
        startService(Intent(this, PreviousTimeVisitedService::class.java))
    }

    private fun initHelper() {
        val bottomNavigationView = binding?.bottomNavView
        val navGraphIds = listOf(
            R.navigation.bottom_home_nav,
            R.navigation.bottom_saved_nav
        )
        // Setup the bottom navigation view with a list of navigation graphs
        navigationHelper = BottomNavigationHelper(
            bottomNavigationView = bottomNavigationView,
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent,
            destinationChangedListener = this
        )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        lifecycle.removeObserver(navigationHelper)
        lifecycle.addObserver(navigationHelper)
        navigationHelper.setupWithNavController()
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
