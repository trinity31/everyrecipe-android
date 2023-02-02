package com.davinciapps.fridgemaster.presentation.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davinciapps.fridgemaster.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llFreezer.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            intent.putExtra("fragment", "freezer")
            startActivity(intent)
        }

        binding.llVegoption.setOnClickListener {
            val intent = Intent(requireActivity(), SettingActivity::class.java)
            intent.putExtra("fragment", "vegoption")
            startActivity(intent)
        }
    }
}