package com.davinciapps.fridgemaster.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.davinciapps.fridgemaster.BuildConfig
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.databinding.ActivityMainBinding
import com.davinciapps.fridgemaster.presentation.viewmodel.*
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

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

    private var mInterstitialAd: InterstitialAd? = null

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

        loadAd()
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

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            BuildConfig.AD_UNIT_ID, //ca-app-pub-3940256099942544~3347511712
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("MainActivity", adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    fun showInterstitial() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad was dismissed.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mInterstitialAd = null
                        loadAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        Log.d(TAG, "Ad failed to show.")
                        // Don't forget to set the ad reference to null so you
                        // don't show the ad a second time.
                        mInterstitialAd = null
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                        // Called when ad is dismissed.
                    }
                }
            mInterstitialAd?.show(this)
        } else {
            Log.d(TAG, "Ad wasn't loaded.")
        }
    }
}