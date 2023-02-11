package com.davinciapps.fridgemaster.presentation.home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.model.RecipeList
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.databinding.ActivitySearchResultBinding
import com.davinciapps.fridgemaster.presentation.adapters.RecipeListAdapter
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {
    private val TAG = SearchResultActivity::class.java.simpleName

    @Inject
    lateinit var factory: BookmarkViewModelFactory
    lateinit var viewModel: BookmarkViewModel

    private lateinit var binding: ActivitySearchResultBinding

    private lateinit var recipeList: RecipeList
    private lateinit var recipes: List<Recipe>

    private lateinit var recipeListAdapter: RecipeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, factory)
            .get(BookmarkViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        recipeList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("recipe_list", RecipeList::class.java)!!
        else
            intent.getSerializableExtra("recipe_list") as RecipeList

        recipes = recipeList.recipes.filter {
            it.recipe != null
        }

        supportActionBar?.let {
            title = intent.getStringExtra("title") ?: resources.getString(R.string.search_result)
            it.setDisplayHomeAsUpEnabled(true)
        }

        recipeListAdapter = RecipeListAdapter(recipes, viewModel, true)
        binding.recyclerView.apply {
            adapter = recipeListAdapter
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
        }
    }

    private fun initData() {
        viewModel.getBookmarkedItems()
    }

    private fun initObserve() {
        viewModel.bookmarks.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Bookmarks.")
                    recipeListAdapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }
        viewModel.bookmark_result.observe(this) {
            viewModel.getBookmarkedItems()
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
