package com.example.everyrecipe.presentation.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.everyrecipe.presentation.setup.SetupActivity
import com.example.everyrecipe.databinding.ActivityLandingBinding
import dagger.hilt.android.AndroidEntryPoint

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomButton.setOnClickListener {
            val intent = Intent(this, SetupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}