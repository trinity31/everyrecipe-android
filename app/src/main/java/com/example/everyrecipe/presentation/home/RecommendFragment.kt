package com.example.everyrecipe.presentation.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.FragmentRecommendBinding
import com.example.everyrecipe.presentation.adapters.RecipeListAdapter
import com.example.everyrecipe.presentation.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class RecommendFragment : Fragment() {
    private val TAG = RecommendFragment::class.java.simpleName

    val REQUEST_CODE = 1

    @Inject
    lateinit var factory: FreezerViewModelFactory
    lateinit var freezerViewModel: FreezerViewModel

    @Inject
    lateinit var bookmarkFactory: BookmarkViewModelFactory
    lateinit var bookmarkViewModel: BookmarkViewModel

    @Inject
    lateinit var viewModelFactory: RecommendViewModelFactory
    lateinit var viewModel: RecommendViewModel

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeListAdapter: RecipeListAdapter

    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            initData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        freezerViewModel = ViewModelProvider(this, factory)
            .get(FreezerViewModel::class.java)

        bookmarkViewModel = ViewModelProvider(this, bookmarkFactory)
            .get(BookmarkViewModel::class.java)

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(RecommendViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        recipeListAdapter = RecipeListAdapter(listOf(), viewModel = bookmarkViewModel)
        binding.recommendRv.apply {
            adapter = recipeListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true;
            initData()
        }

        binding.llFreezer.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            intent.putExtra("fragment", "freezer")
            launcher.launch(intent)
            binding.llFreezer.visibility = View.INVISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initData() {
        freezerViewModel.getFreezerExistingItems()
        bookmarkViewModel.getBookmarkedItems()
    }

    private fun initObserve() {
        freezerViewModel.existingItems.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.i(TAG, "Successfully fetched.${it.data?.size} items in the freezer.")
                    it.data?.let { list ->
                        if(list.size > 0) {
                            viewModel.getRecommendedRecipes(list)
                        } else {
                            binding.llFreezer.visibility = View.VISIBLE
                        }
                        binding.swipeRefreshLayout.isRefreshing = false;
                    }

                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {binding.progressBar.visibility = View.VISIBLE}
            }
        }

        viewModel.recipes.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.i(TAG, "Successfully fetched.${it.data?.size} recipes.")
                    it.data?.let { recipes ->
                        recipeListAdapter.recipes = recipes
                        recipeListAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.i(TAG, "Failed to fetch recipes. ${it.message}")
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        bookmarkViewModel.bookmarks.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Bookmarks.")
                    recipeListAdapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }

        bookmarkViewModel.bookmark_result.observe(viewLifecycleOwner) {
            bookmarkViewModel.getBookmarkedItems()
        }
    }

}