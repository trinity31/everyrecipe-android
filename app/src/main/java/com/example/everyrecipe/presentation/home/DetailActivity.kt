package com.example.everyrecipe.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.everyrecipe.R
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.ActivityDetailBinding
import com.example.everyrecipe.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var recipeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        supportActionBar?.let {
            title = intent.getStringExtra("title")
            it.setDisplayHomeAsUpEnabled(true)
        }

        Glide.with(binding.imageView.context)
            .load(intent.getStringExtra("imageUrl"))
            .into(binding.imageView)

        binding.tvDetail.text = intent.getStringExtra("description")

        recipeId = intent.getStringExtra("id") ?: ""
    }

    private fun initData() {
        viewModel.getIngredients(recipeId)
        viewModel.getProcedures(recipeId)
    }

    private fun initObserve() {
        viewModel.ingredients.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Ingredirents.")
                }
                else -> {}
            }
        }
        viewModel.procedures.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Procedures.")
                    it.data?.forEach {
                        Log.i(TAG, "${it}")
                    }
                }
                else -> {}
            }
        }
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