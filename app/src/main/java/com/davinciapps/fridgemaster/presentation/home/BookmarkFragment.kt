package com.davinciapps.fridgemaster.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.databinding.FragmentBookmarkBinding
import com.davinciapps.fridgemaster.presentation.adapters.RecipeListAdapter
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : Fragment() {
    private val TAG = BookmarkFragment::class.java.simpleName

    @Inject
    lateinit var bookmarkFactory: BookmarkViewModelFactory
    lateinit var bookmarkViewModel: BookmarkViewModel

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeListAdapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bookmarkViewModel = ViewModelProvider(requireActivity())
            .get(BookmarkViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        recipeListAdapter = RecipeListAdapter(listOf(), viewModel = bookmarkViewModel, true)
        binding.bookmarkRv.apply {
            adapter = recipeListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initData() {
        bookmarkViewModel.getBookmarkedItems()
    }

    private fun initObserve() {
        bookmarkViewModel.bookmarks.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Bookmarks.")
                    it.data?.let { recipes ->
                        recipeListAdapter.recipes = recipes
                        recipeListAdapter.notifyDataSetChanged()
                    }
                }
                else -> {}
            }
        }

        bookmarkViewModel.bookmark_result.observe(viewLifecycleOwner) {
            bookmarkViewModel.getBookmarkedItems()
        }
    }
}