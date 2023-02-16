package com.davinciapps.fridgemaster.presentation.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.data.model.RecipeList
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.databinding.FragmentSearchBinding
import com.davinciapps.fridgemaster.presentation.adapters.FoodSuggestAdapter
import com.davinciapps.fridgemaster.presentation.viewmodel.FreezerViewModel
import com.davinciapps.fridgemaster.presentation.viewmodel.FreezerViewModelFactory
import com.davinciapps.fridgemaster.presentation.viewmodel.SearchViewModel
import com.davinciapps.fridgemaster.presentation.viewmodel.SearchViewModelFactory
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment(), SearchView.OnQueryTextListener {
    private val TAG = SearchFragment::class.java.simpleName

    @Inject
    lateinit var freezerFactory: FreezerViewModelFactory
    lateinit var freezerViewModel: FreezerViewModel

    @Inject
    lateinit var searchFactory: SearchViewModelFactory
    lateinit var searchViewModel: SearchViewModel

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

        freezerViewModel = ViewModelProvider(requireActivity())
            .get(FreezerViewModel::class.java)

        searchViewModel = ViewModelProvider(this, searchFactory)
            .get(SearchViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    override fun onResume() {
        super.onResume()
        removeAllChips()
        freezerViewModel.getFreezerExistingItems()
    }

    private fun initView() {
        binding.searchView.setOnQueryTextListener(this)
        foodSuggestAdapter = FoodSuggestAdapter(listOf())
        binding.suggestRv.apply {
            adapter = foodSuggestAdapter
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            foodSuggestAdapter.setOnItemClickListener(object: FoodSuggestAdapter.OnItemClickListener {
                override fun onItemClick(food: FreezerItem) {
                   // val item = FreezerItem(food.id, food.categoryId, food.name)
                    searchViewModel.addToSearchFoods(food)
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
                searchViewModel.cancelSearchFoods()
                return true
            }
        })

        binding.searchBtn.setOnClickListener {
            searchViewModel.searchRecipes()
        }
    }

    private fun initData() {
        searchViewModel.getAllFoods()
        freezerViewModel.getFreezerExistingItems()
    }

    private fun initObserve() {
        searchViewModel.foods.observe(viewLifecycleOwner) {
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

        searchViewModel.filteredFoods.observe(viewLifecycleOwner) {
            if(it.size > 0) {
                foodSuggestAdapter.foods = it
                foodSuggestAdapter.notifyDataSetChanged()
                binding.suggestRv.visibility = View.VISIBLE
            }
        }

        freezerViewModel.existingItems.observe(viewLifecycleOwner) {
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

        searchViewModel.recipes.observe(viewLifecycleOwner) {
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
                searchViewModel.addToSearchFoods(item)
                addFoodToChipGroup(item.name)
            } else {
                removeFromSearchFood(item.name)
            }
        }
    }

    private fun removeAllChips() {
        // Loop through all the chips in the group and remove them
//        binding.freezerChip.apply {
//            for (i in 0 until this.childCount) {
//                val chip = this.getChildAt(i)
//                this.removeView(chip)
//            }
//        }

        binding.freezerChip.removeAllViews()
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
        val index = searchViewModel.removeFromSearchFoods(name)
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
            searchViewModel.searchFood(newText)
        }
        return true
    }
}