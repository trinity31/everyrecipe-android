package com.davinciapps.fridgemaster.presentation.home

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.model.FreezerItemList
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.model.RecipeList
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.databinding.ActivitySearchResultPagingBinding
import com.davinciapps.fridgemaster.presentation.adapters.SearchResultPagingAdapter
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModelFactory
import com.davinciapps.fridgemaster.presentation.viewmodel.GooglePagingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SearchResultPagingActivity : AppCompatActivity() {
    private val TAG = SearchResultPagingActivity::class.java.simpleName

    private lateinit var binding: ActivitySearchResultPagingBinding
    private val viewModel: GooglePagingViewModel by viewModels()

    @Inject
    lateinit var factory: BookmarkViewModelFactory
    lateinit var bookmarkViewModel: BookmarkViewModel

    private lateinit var recipeItemAdapter: SearchResultPagingAdapter

    private lateinit var freezerItemList: FreezerItemList
    private lateinit var freezerItems: List<FreezerItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultPagingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookmarkViewModel = ViewModelProvider(this, factory)
            .get(BookmarkViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        freezerItemList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("freezer_item_list", FreezerItemList::class.java)!!
        else
            intent.getSerializableExtra("freezer_item_list") as FreezerItemList

        freezerItems = freezerItemList.items

        supportActionBar?.let {
            title = intent.getStringExtra("title") ?: resources.getString(R.string.search_result)
            it.setDisplayHomeAsUpEnabled(true)
        }

        recipeItemAdapter = SearchResultPagingAdapter(bookmarkViewModel)
        binding.recyclerView.apply {
            adapter = recipeItemAdapter
            layoutManager = LinearLayoutManager(this@SearchResultPagingActivity)
        }

    }

    private fun initData() {
        bookmarkViewModel.getBookmarkedItems()

        lifecycleScope.launch {
            viewModel.search(freezerItems).collectLatest {
                recipeItemAdapter.submitData(it)
            }
        }
    }

    private fun initObserve() {
        bookmarkViewModel.bookmarks.observe(this) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Bookmarks.")
                    recipeItemAdapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }
        bookmarkViewModel.bookmark_result.observe(this) {
            bookmarkViewModel.getBookmarkedItems()
        }
    }
}