package com.han.subway_app.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.han.subway_app.R
import com.han.subway_app.databinding.ActivityMainBinding
import com.han.subway_app.extension.toGone
import com.han.subway_app.extension.toVisible
import com.han.subway_app.presentation.stationarrivals.StationArrivalsFragment
import com.han.subway_app.presentation.stationarrivals.StationArrivalsFragmentArgs

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
        bindViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolBar)
        setupActionBarWithNavController(navigationController)
    }

    private fun bindViews() {
        navigationController.addOnDestinationChangedListener { _, destination, argument ->
            if (destination.id == R.id.station_arrivals_dest) {
                title = StationArrivalsFragmentArgs.fromBundle(argument!!).station.name
                binding.toolBar.toVisible()
            } else {
                binding.toolBar.toGone()
            }
        }
    }
}