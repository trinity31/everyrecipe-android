package com.davinciapps.fridgemaster.presentation.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.data.model.RecipeList
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.databinding.FragmentRecommendBinding
import com.davinciapps.fridgemaster.presentation.adapters.RecommendCardAdapter
import com.davinciapps.fridgemaster.presentation.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
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

    private lateinit var recommendCardAdapter1: RecommendCardAdapter
    private lateinit var recommendCardAdapter2: RecommendCardAdapter

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

        freezerViewModel = ViewModelProvider(requireActivity())
            .get(FreezerViewModel::class.java)

        bookmarkViewModel = ViewModelProvider(requireActivity())
            .get(BookmarkViewModel::class.java)

        viewModel = ViewModelProvider(requireActivity())
            .get(RecommendViewModel::class.java)

        initView()
        initData()
        initObserve()
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume")
        bookmarkViewModel.getBookmarkedItems()
    }

    private fun initView() {
        recommendCardAdapter1 = RecommendCardAdapter(resources.getString(R.string.recommend_card_title_1), listOf(), viewModel = bookmarkViewModel, true)
        recommendCardAdapter2 = RecommendCardAdapter(resources.getString(R.string.recommend_card_title_2), listOf(), viewModel = bookmarkViewModel, false)
        val concatenatedAdapter = ConcatAdapter(recommendCardAdapter1, recommendCardAdapter2)

        binding.recommendRv.apply {
            adapter = concatenatedAdapter
            layoutManager = LinearLayoutManager(activity)
            val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            ContextCompat.getDrawable(context, R.drawable.divider)
                ?.let { itemDecoration.setDrawable(it) }
            addItemDecoration(itemDecoration)
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
//                            if(viewModel.recipesWeb.value?.data.isNullOrEmpty()) {
//                                viewModel.getRecommendedWebRecipes(list)
//                            }
                        } else {
                            binding.llFreezer.visibility = View.VISIBLE
                        }
                        binding.swipeRefreshLayout.isRefreshing = false;
                    }

                }
                is Resource.Error -> {
                    binding.llProgress.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    binding.llProgress.visibility = View.VISIBLE
                }
            }
        }

        viewModel.recipes.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    //binding.llProgress.visibility = View.INVISIBLE
                    recommendCardAdapter1.loading = false
                    Log.i(TAG, "Successfully fetched ${it.data?.size} basic recipes.")
                    it.data?.let { recipes ->
                        recommendCardAdapter1.recipes = recipes.take(4)
                        recommendCardAdapter1.setMoreClickListener(object :
                            RecommendCardAdapter.OnMoreClickListener {
                            override fun onMoreClick() {
                                val intent = Intent(requireActivity(), SearchResultActivity::class.java)
                                intent.putExtra("title", resources.getString(R.string.recommend_card_title_1))
                                intent.putExtra("recipe_list", RecipeList(recipes) as Serializable)
                                startActivity(intent)
                            }

                            override fun onRefreshClick() {
                                initData()
                            }
                        })
                    }
                }
                is Resource.Error -> {
                    //binding.llProgress.visibility = View.INVISIBLE
                    recommendCardAdapter1.loading = false
                    Log.i(TAG, "Failed to fetch basic recipes ${it.message}")
                }
                is Resource.Loading -> {
                    //binding.llProgress.visibility = View.VISIBLE
                    recommendCardAdapter1.loading = true
                }
            }
            recommendCardAdapter1.notifyDataSetChanged()
        }

        viewModel.recipesWeb.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    //binding.llProgress.visibility = View.INVISIBLE
                    recommendCardAdapter2.loading = false
                    Log.i(TAG, "Successfully fetched ${it.data?.size} web recipes.")
                    it.data?.let { recipes ->
                        recommendCardAdapter2.recipes = recipes.take(4)
                        recommendCardAdapter2.setMoreClickListener(object :
                            RecommendCardAdapter.OnMoreClickListener {
                            override fun onMoreClick() {
                                val intent = Intent(requireActivity(), SearchResultActivity::class.java)
                                intent.putExtra("title", resources.getString(R.string.recommend_card_title_2))
                                intent.putExtra("recipe_list", RecipeList(recipes) as Serializable)
//                                intent.putExtra("freezer_item_list", freezerViewModel.existingItems.value?.data?.let { it1 ->
//                                    FreezerItemList(
//                                        it1
//                                    )
//                                } as Serializable)
                                startActivity(intent)
                            }

                            override fun onRefreshClick() {}
                        })
                    }
                }
                is Resource.Error -> {
                    //binding.llProgress.visibility = View.INVISIBLE
                    recommendCardAdapter2.loading = false
                    Log.i(TAG, "Failed to fetch web recipes. ${it.message}")
                }
                is Resource.Loading -> {
                    //binding.llProgress.visibility = View.VISIBLE
                    recommendCardAdapter2.loading = true
                }
            }
            recommendCardAdapter2.notifyDataSetChanged()
        }

        bookmarkViewModel.bookmarks.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    Log.i(TAG, "Fetched Bookmarks.")
                    recommendCardAdapter1.notifyDataSetChanged()
                }
                else -> {}
            }
        }

        bookmarkViewModel.bookmark_result.observe(viewLifecycleOwner) {
            bookmarkViewModel.getBookmarkedItems()
        }
    }

}