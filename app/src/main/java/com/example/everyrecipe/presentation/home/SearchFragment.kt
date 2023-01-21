package com.example.everyrecipe.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Food
import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.model.Recipe
import com.example.everyrecipe.data.model.RecipeList
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.FragmentSearchBinding
import com.example.everyrecipe.presentation.adapters.FoodSuggestAdapter
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModel
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModelFactory
import com.example.everyrecipe.presentation.viewmodel.SearchViewModel
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private val TAG = SearchFragment::class.java.simpleName

    @Inject
    lateinit var factory: FreezerViewModelFactory
    lateinit var freezerViewModel: FreezerViewModel

    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var foodSuggestAdapter: FoodSuggestAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
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
        binding.searchView.setOnQueryTextListener(this)
        foodSuggestAdapter = FoodSuggestAdapter(listOf())
        binding.suggestRv.apply {
            adapter = foodSuggestAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            foodSuggestAdapter.setOnItemClickListener(object: FoodSuggestAdapter.OnItemClickListener {
                override fun onItemClick(food: Food) {
                    val item = FreezerItem(food.id, food.category.id, food.name)
                    viewModel.addToSearchFoods(item)
                    addFoodToChipGroup(food.name)
                    binding.searchView.setQuery("", false)
                    binding.searchView.isIconified = true
                    visibility = View.INVISIBLE
                }
            })
        }
        binding.searchView.setOnCloseListener(object: SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                Log.i(TAG, "SearchView closed.")
                binding.suggestRv.visibility = View.INVISIBLE
                viewModel.cancelSearchFoods()
                return true
            }
        })

        binding.searchBtn.setOnClickListener {
            viewModel.searchRecipes()
        }
    }

    private fun initData() {
        viewModel.getAllFoods()
        freezerViewModel.getFreezerItems()
    }

    private fun initObserve() {
        viewModel.foods.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Log.i(TAG, "Successfully fetched.${it.data?.size} foods.")
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        viewModel.filteredFoods.observe(viewLifecycleOwner) {
            if(it.size > 0) {
                foodSuggestAdapter.foods = it
                foodSuggestAdapter.notifyDataSetChanged()
                binding.suggestRv.visibility = View.VISIBLE
            }
        }

        freezerViewModel.freezerItems.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.i(TAG, "Successfully fetched.${it.data?.size} items in the freezer.")
                    val items = (it.data) as List<FreezerItem>
                    items.forEach {
                        addFreezerItemsToChipGroup(it)
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }

        viewModel.recipes.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.i(TAG, "Successfully fetched ${it.data?.size} recipes.")
                    val items = (it.data) as List<Recipe>
                    val intent = Intent(requireActivity(), SearchResultActivity::class.java)
                    intent.putExtra("recipe_list", RecipeList(items) as Serializable)
                    startActivity(intent)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun addFoodToChipGroup(name: String) {
        val chip = Chip(context)
        chip.text = name
        binding.foodChip.addView(chip)
        chip.isCloseIconVisible = true
        chip.setOnCloseIconClickListener {
            removeFromSearchFood(chip.text as String)
            uncheckFreezerItem(chip.text as String)
        }
    }

    private fun addFreezerItemsToChipGroup(item: FreezerItem) {
        val chip = Chip(context)
        chip.text = item.name
        chip.isCheckable = true
        binding.freezerChip.addView(chip)
        chip.isCloseIconVisible = false
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
               viewModel.addToSearchFoods(item)
                addFoodToChipGroup(item.name)
            } else {
                removeFromSearchFood(item.name)
            }
        }
    }

    private fun uncheckFreezerItem(name: String) {
        for (i in 0 until binding.freezerChip.getChildCount()) {
            val chip = binding.freezerChip.getChildAt(i) as Chip
            if (chip.text.toString() == name) {
                chip.isChecked = false
            }
        }
    }

    private fun removeFromSearchFood(name: String) {
        val index = viewModel.removeFromSearchFoods(name)
        if(index != -1) {
            binding.foodChip.removeView(binding.foodChip.getChildAt(index))
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.i(TAG, "onQueryTextSubmit, newText: $query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(!newText.isNullOrEmpty()) {
            viewModel.searchFood(newText)
        }
        return true
    }
}