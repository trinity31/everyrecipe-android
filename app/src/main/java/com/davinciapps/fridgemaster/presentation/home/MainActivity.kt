package com.davinciapps.fridgemaster.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.databinding.ActivityMainBinding
import com.davinciapps.fridgemaster.presentation.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val recommendFragment = RecommendFragment()
    private val searchFragment = SearchFragment()
    private val bookmarkFragment = BookmarkFragment()
    private val settingFragment = SettingFragment()

    private var activeFragment: Fragment = recommendFragment

    @Inject
    lateinit var factory: FreezerViewModelFactory
    lateinit var freezerViewModel: FreezerViewModel

    @Inject
    lateinit var bookmarkFactory: BookmarkViewModelFactory
    lateinit var bookmarkViewModel: BookmarkViewModel

    @Inject
    lateinit var recommendFactory: RecommendViewModelFactory
    lateinit var recommendViewModel: RecommendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.let {
            title = resources.getString(R.string.app_name)
        }

        setUpBottomNavigation()

        freezerViewModel = ViewModelProvider(this, factory)
            .get(FreezerViewModel::class.java)

        bookmarkViewModel = ViewModelProvider(this, bookmarkFactory)
            .get(BookmarkViewModel::class.java)

        recommendViewModel = ViewModelProvider(this, recommendFactory)
            .get(RecommendViewModel::class.java)
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