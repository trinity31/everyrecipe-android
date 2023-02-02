package com.davinciapps.fridgemaster.presentation.setup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.davinciapps.fridgemaster.databinding.FragmentVegoptionBinding
import com.davinciapps.fridgemaster.presentation.types.Constants


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class VegOptionFragment : Fragment() {

    private var _binding: FragmentVegoptionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var vegType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentVegoptionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        vegType = sharedPref.getString(Constants.VEGTYPE, Constants.NONE).toString()

        initButtons()
    }

    private fun initButtons() {
        val count: Int = binding.vegtypeGroup.getChildCount()
        for (i in 0 until count) {
            val o: RadioButton = binding.vegtypeGroup.getChildAt(i) as RadioButton
            if (o.text.toString() == vegType) {
                o.isChecked = true
                break
            }
        }

        binding.vegtypeGroup.setOnCheckedChangeListener { group, checkedId -> // Get the checked radio button
            val checkedRadioButton = group.findViewById<RadioButton>(checkedId)
            val checkedRadioButtonText = checkedRadioButton.text as String

            val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
            sharedPref.edit().putString(Constants.VEGTYPE, checkedRadioButtonText).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}