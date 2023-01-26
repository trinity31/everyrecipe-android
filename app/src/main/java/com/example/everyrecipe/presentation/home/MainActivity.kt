package com.example.everyrecipe.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import com.example.everyrecipe.R
import com.example.everyrecipe.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var recommendFragment: RecommendFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var bookmarkFragment: BookmarkFragment
    private lateinit var settingFragment: SettingFragment

    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        recommendFragment = RecommendFragment()
        searchFragment = SearchFragment()
        bookmarkFragment = BookmarkFragment()
        settingFragment = SettingFragment()
        
        changeFragment(recommendFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment = when(it.itemId) {
                R.id.recommendFragment -> recommendFragment
                R.id.searchFragment -> searchFragment
                R.id.bookmarkFragment -> bookmarkFragment
                else -> settingFragment
            }
            changeFragment(fragment)
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .commit()
    }
}