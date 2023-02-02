package com.davinciapps.fridgemaster.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.databinding.ActivityMainBinding
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

        supportActionBar?.let {
            title = resources.getString(R.string.app_name)
        }

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