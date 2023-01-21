package com.example.everyrecipe.presentation.home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everyrecipe.R
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.model.RecipeList
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.ActivitySearchResultBinding
import com.example.everyrecipe.presentation.adapters.RecipeListAdapter
import com.example.everyrecipe.presentation.viewmodel.BookmarkViewModel
import com.example.everyrecipe.presentation.viewmodel.BookmarkViewModelFactory
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

    private fun initObserve() {
        viewModel.bookmarks.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Bookmarks.")
                }
                else -> {}
            }
        }
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
            title = resources.getString(R.string.search_result)
            it.setDisplayHomeAsUpEnabled(true)
        }

        recipeListAdapter = RecipeListAdapter(recipes)
        binding.recyclerView.apply {
            adapter = recipeListAdapter
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
        }
        recipeListAdapter.setOnItemClickListener(object: RecipeListAdapter.OnItemClickListener {
            override fun onItemClick(recipe: Recipe) {
                Log.i(TAG, "Recipe clicked: $recipe")
                //Add or Remve Bookmark
            }
        })
    }

    private fun initData() {
        viewModel.getBookmarkedItems()
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
