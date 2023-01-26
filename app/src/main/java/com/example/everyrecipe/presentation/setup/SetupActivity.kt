package com.example.everyrecipe.presentation.setup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.preference.PreferenceManager
import com.example.everyrecipe.R
import com.example.everyrecipe.databinding.ActivitySetupBinding
import com.example.everyrecipe.presentation.home.MainActivity
import com.example.everyrecipe.presentation.types.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_setup)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val vegType = sharedPref.getString(Constants.VEGTYPE, null)
        if(vegType != null) {
            navController.navigate(R.id.action_vegoptionFragment_to_freezerFragment)
        }

        binding.buttonBottom.setOnClickListener {
            when(navController.currentDestination?.label) {
                 resources.getString(R.string.freezer_fragment_label) -> {
                     val intent = Intent(this, MainActivity::class.java)
                     startActivity(intent)
                     finish()
                }
                resources.getString(R.string.vegoption_fragment_label) -> {
                    navController.navigate(R.id.action_vegoptionFragment_to_freezerFragment)
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_setup)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}