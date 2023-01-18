package com.example.everyrecipe.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everyrecipe.R
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.FragmentFreezerBinding
import com.example.everyrecipe.databinding.FragmentRecommendBinding
import com.example.everyrecipe.presentation.adapters.RecipeListAdapter
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModel
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModelFactory
import com.example.everyrecipe.presentation.viewmodel.RecommendViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecommendFragment : Fragment() {
    private val TAG = RecommendFragment::class.java.simpleName

    @Inject
    lateinit var factory: FreezerViewModelFactory
    lateinit var freezerViewModel: FreezerViewModel

    private val viewModel: RecommendViewModel by viewModels()

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var recipeListAdapter: RecipeListAdapter

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

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        recipeListAdapter = RecipeListAdapter(listOf())
        binding.recommendRv.apply {
            adapter = recipeListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initData() {
        freezerViewModel.getFreezerItems()
    }

    private fun initObserve() {
        freezerViewModel.freezerItems.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.i(TAG, "Successfully fetched.${it.data?.size} items in the freezer.")
                    it.data?.let {
                        viewModel.getRecommendedRecipes(it)
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
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
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

}