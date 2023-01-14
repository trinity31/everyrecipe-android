package com.example.everyrecipe.presentation.setup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Category
import com.example.everyrecipe.R
import com.example.everyrecipe.data.types.CategoryType
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.FragmentFreezerBinding
import com.example.everyrecipe.presentation.adapters.FreezerCategoryAdapter
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModel
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FreezerFragment : Fragment() {
    private val TAG = FreezerFragment::class.java.simpleName

    //private val viewModel: FreezerViewModel by viewModels()
    @Inject
    lateinit var factory: FreezerViewModelFactory
    lateinit var viewModel: FreezerViewModel
    private var _binding: FragmentFreezerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val concatAdapter = ConcatAdapter()

    private var categories = listOf<Category>()

    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFreezerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, factory)
            .get(FreezerViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        binding.freezerRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }

        binding.buttonSkip.setOnClickListener {
            findNavController().navigate(R.id.action_freezerFragment_to_vegoptionFragment)
        }
    }

    private fun initData() {
        viewModel.getCategories()
        viewModel.getFreezerItems()
    }

    private fun initObserve() {
        viewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE

                    val filteredData = it.data?.filter {
                        CategoryType.getValue(it.id).isAllowed()
                    }

                    val sortedData = filteredData?.sortedBy {
                        it.order
                    }

                    sortedData?.let {
                        categories = it
                        if(categories.size > 0) {
                            setAdapters(currentIndex) //currentIndex should be 0 here
                        }
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

        viewModel.foods.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val foods = it.data
                    if(!foods.isNullOrEmpty()) {
                        //Log.i(TAG, "id: ${foods.get(0).category.id}, name: ${foods.get(0).category.name} ")
                        val adapter = concatAdapter.adapters[currentIndex]
                        if(adapter != null) {
                            (adapter as FreezerCategoryAdapter).foods = foods
                            adapter.notifyItemChanged(0)
                            if(categories.size > currentIndex + 1) {
                                currentIndex++
                                setAdapters(currentIndex)
                            }
                        }
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }

        viewModel.freezerItems.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    Log.i(TAG, "Successfully fetched freezer items.")
//                    val items = it.data
//                    items?.forEach {
//                        Log.i(TAG, it.name)
//                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun setAdapters(position: Int) {
        concatAdapter.addAdapter(FreezerCategoryAdapter(categories[position].name, listOf(), viewModel))
        viewModel.getFoodsByCategory(categories[position].id)

        if(position == 0) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}