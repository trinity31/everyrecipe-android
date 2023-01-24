package com.example.everyrecipe.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.example.everyrecipe.R
import com.example.everyrecipe.databinding.ActivitySettingBinding
import com.example.everyrecipe.presentation.setup.FreezerFragment
import com.example.everyrecipe.presentation.setup.VegOptionFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : AppCompatActivity() {
    private val TAG = SettingActivity::class.java.simpleName

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        supportActionBar?.let {
            title = when(intent.getStringExtra("fragment")) {
                "freezer" -> resources.getString(R.string.freezer_fragment_label)
                else -> resources.getString(R.string.vegoption_fragment_label)
            }
            it.setDisplayHomeAsUpEnabled(true)
        }

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = when(intent.getStringExtra("fragment")) {
            "freezer" -> FreezerFragment()
            else -> VegOptionFragment()
        }

        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}