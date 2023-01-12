package com.example.everyrecipe.presentation.setup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Category
import com.example.everyrecipe.R
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.databinding.FragmentFreezerBinding
import com.example.everyrecipe.presentation.adapters.FreezerCategoryAdapter
import com.example.everyrecipe.presentation.viewmodel.FreezerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

@AndroidEntryPoint
class FreezerFragment : Fragment() {
    private val TAG = FreezerFragment::class.java.simpleName

    private val viewModel: FreezerViewModel by viewModels()

    private var _binding: FragmentFreezerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val concatAdapter = ConcatAdapter()

    private var categories = listOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFreezerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.freezerRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = concatAdapter
        }

        binding.buttonSkip.setOnClickListener {
            findNavController().navigate(R.id.action_freezerFragment_to_vegoptionFragment)
        }

        viewModel.getCategories()

        viewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE

                    val sortedData = it.data?.sortedBy {
                        it.order
                    }
//                    sortedData?.forEach {
//                        concatAdapter.addAdapter(FreezerCategoryAdapter(it.name, listOf()))
//                        viewModel.getFoodsByCategory(it.id)
//                    }
                    sortedData?.let {
                        categories = it
                        if(categories.size > 0) {
                            setAdapters(0)
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
                        val order = foods.get(0).category.order
                        //Log.i(TAG, "order: $order")
                        val adapter = concatAdapter.adapters[order - 1]
                        if(adapter != null) {
                            (adapter as FreezerCategoryAdapter).foods = foods
                            adapter.notifyItemChanged(0)
                            if(categories.size > order) {
                                setAdapters(order)
                            }
                        } else {
                            Log.i(TAG, "adapter for order $order is null")
                        }
                    }
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }
    }

    private fun setAdapters(position: Int) {
        concatAdapter.addAdapter(FreezerCategoryAdapter(categories[position].name, listOf()))
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