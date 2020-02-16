package com.wikisearch.samsruti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        setupNavigation()
        setupActionBar()

    }

    private fun setupNavigation() {
        navigationController = findNavController(R.id.nav_host_main_fragment)
        appBarConfiguration = AppBarConfiguration(navigationController.graph)

        navigationController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id){
                R.id.mainFragment -> {
                    destination.label = "Enguru Speech"
                }
                R.id.wikiDetails -> {
                    destination.label = "Details"
                }
            }
        }

    }

    private fun setupActionBar() {
        setupActionBarWithNavController(navigationController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navigationController = findNavController(R.id.nav_host_main_fragment)
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

}
