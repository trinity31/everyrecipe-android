package com.example.everyrecipe.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.everyrecipe.data.model.Ingredient
import com.example.everyrecipe.data.model.IngredientList
import com.example.everyrecipe.data.model.ProcedureList
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.ActivityDetailBinding
import com.example.everyrecipe.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable


@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val TAG = DetailActivity::class.java.simpleName

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var recipeId: String

    private lateinit var ingredientsFragment: IngredientsFragment
    private lateinit var proceduresFragment: ProceduresFragment

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

        binding.btnIngredient.setOnClickListener {
            showIngredients()
        }
        binding.btnProcedure.setOnClickListener {
            showProcedures()
        }
    }

    private fun initData() {
        recipeId = intent.getStringExtra("id") ?: ""

        viewModel.getIngredients(recipeId)
        viewModel.getProcedures(recipeId)
    }

    private fun initObserve() {
        viewModel.ingredients.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Ingredirents.")
                    it.data?.let {
                        if(!::ingredientsFragment.isInitialized) {
                            ingredientsFragment = IngredientsFragment.newInstance(IngredientList(it))
                        }
                        showIngredients()
                    }
                }
                else -> {}
            }
        }
        viewModel.procedures.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Procedures.")
                    it.data?.let {
                        if(!::proceduresFragment.isInitialized) {
                            proceduresFragment = ProceduresFragment.newInstance(ProcedureList(it))
                        }
                    }
                }
                else -> {}
            }
        }
    }

    private fun showIngredients() {
        if(::ingredientsFragment.isInitialized) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(com.example.everyrecipe.R.id.fragment_container, ingredientsFragment)
            transaction.commit()
        }
    }

    private fun showProcedures() {
        if(::proceduresFragment.isInitialized) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(com.example.everyrecipe.R.id.fragment_container, proceduresFragment)
            transaction.commit()
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