package com.example.everyrecipe.presentation.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.presentation.setup.SetupActivity
import com.example.everyrecipe.databinding.ActivityLandingBinding
import com.example.everyrecipe.presentation.viewmodel.LandingViewModel
import com.example.everyrecipe.presentation.viewmodel.SearchViewModel
import com.example.everyrecipe.presentation.viewmodel.SearchViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    //private val viewModel: LandingViewModel by viewModels()

    @Inject
    lateinit var searchFactory: SearchViewModelFactory
    lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomButton.setOnClickListener {
            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
            finish()
        }

        searchViewModel = ViewModelProvider(this, searchFactory)
            .get(SearchViewModel::class.java)

        searchViewModel.getAllFoods()

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                //viewModel.isLoading.value
                searchViewModel.foods.value is Resource.Loading
            }
        }
    }
}