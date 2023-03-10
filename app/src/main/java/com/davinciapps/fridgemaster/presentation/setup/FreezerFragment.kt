package com.davinciapps.fridgemaster.presentation.setup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.amplifyframework.datastore.generated.model.Category
import com.davinciapps.fridgemaster.presentation.types.CategoryType
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.databinding.FragmentFreezerBinding
import com.davinciapps.fridgemaster.presentation.adapters.FreezerCategoryAdapter
import com.davinciapps.fridgemaster.presentation.types.Constants
import com.davinciapps.fridgemaster.presentation.viewmodel.FreezerViewModel
import com.davinciapps.fridgemaster.presentation.viewmodel.FreezerViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FreezerFragment : Fragment() {
    private val TAG = FreezerFragment::class.java.simpleName

    @Inject
    lateinit var factory: FreezerViewModelFactory
    lateinit var viewModel: FreezerViewModel
    private var _binding: FragmentFreezerBinding? = null

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
    }

    private fun initData() {
        viewModel.getCategories()
    }

    private fun initObserve() {
        viewModel.categories.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE

                    val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    val vegType = sharedPref.getString(Constants.VEGTYPE, Constants.NONE).toString()

                    val filteredData = it.data?.filter {
                        CategoryType.getValue(it.id).isAllowed(vegType)
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

        viewModel.items.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    val freezerItems = it.data
                    if(!freezerItems.isNullOrEmpty()) {
                        //Log.i(TAG, "id: ${foods.get(0).category.id}, name: ${foods.get(0).category.name} ")
                        val adapter = concatAdapter.adapters[currentIndex]
                        if(adapter != null) {
                            (adapter as FreezerCategoryAdapter).items = freezerItems
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
    }

    private fun setAdapters(position: Int) {
        concatAdapter.addAdapter(FreezerCategoryAdapter(categories[position].name, listOf(), viewModel))
        viewModel.getItemsByCategory(categories[position].id)

        if(position == 0) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}