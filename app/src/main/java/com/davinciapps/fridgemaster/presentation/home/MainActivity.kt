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

//    private lateinit var recommendFragment: RecommendFragment
//    private lateinit var searchFragment: SearchFragment
//    private lateinit var bookmarkFragment: BookmarkFragment
//    private lateinit var settingFragment: SettingFragment

    private val recommendFragment = RecommendFragment()
    private val searchFragment = SearchFragment()
    private val bookmarkFragment = BookmarkFragment()
    private val settingFragment = SettingFragment()

    private var activeFragment: Fragment = recommendFragment

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
        //changeFragment(recommendFragment)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, settingFragment).hide(settingFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, bookmarkFragment).hide(bookmarkFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, searchFragment).hide(searchFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, recommendFragment).commit()

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
            .hide(activeFragment)
            .show(fragment)
            .commit()
        activeFragment = fragment
    }
}