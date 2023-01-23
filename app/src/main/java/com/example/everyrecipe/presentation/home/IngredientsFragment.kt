package com.example.everyrecipe.presentation.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everyrecipe.R
import com.example.everyrecipe.data.model.Ingredient
import com.example.everyrecipe.data.model.IngredientList
import com.example.everyrecipe.databinding.FragmentIngredientsBinding
import com.example.everyrecipe.presentation.adapters.IngredientsAdapter
import java.io.Serializable

private const val ARG_ING = "ingredients"

/**
 * A simple [Fragment] subclass.
 * Use the [IngredientsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IngredientsFragment : Fragment() {
    private val TAG = IngredientsFragment::class.java.simpleName

    private var _binding: FragmentIngredientsBinding?= null
    private val binding get() = _binding!!

    private lateinit var ingredients: IngredientList

    private lateinit var ingredientsAdapter: IngredientsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ingredients = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                it.getSerializable(ARG_ING, IngredientList::class.java)!!
            else
                it.getSerializable(ARG_ING) as IngredientList

            //Log.i(TAG , "ingredients: $ingredients")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        ingredientsAdapter = IngredientsAdapter(ingredients.ingredients)
        binding.rvIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment IngredientsFragment.
         */
        @JvmStatic
        fun newInstance(ingList: IngredientList) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ING, ingList)
                }
            }
    }
}