package com.davinciapps.fridgemaster.presentation.home

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.davinciapps.fridgemaster.data.model.ProcedureList
import com.davinciapps.fridgemaster.databinding.FragmentProceduresBinding
import com.davinciapps.fridgemaster.presentation.adapters.ProceduresAdapter


private const val ARG_PROC = "procedures"

/**
 * A simple [Fragment] subclass.
 * Use the [ProceduresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProceduresFragment : Fragment() {
    private val TAG = ProceduresFragment::class.java.simpleName

    private var _binding: FragmentProceduresBinding?= null
    private val binding get() = _binding!!

    private lateinit var procedures: ProcedureList

    private lateinit var proceduresAdapter: ProceduresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            procedures = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                it.getSerializable(ARG_PROC, ProcedureList::class.java)!!
            else
                it.getSerializable(ARG_PROC) as ProcedureList
            //Log.i(TAG , "procedures: $procedures")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProceduresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        proceduresAdapter = ProceduresAdapter(procedures.procedures)
        binding.rvProcedures.apply {
            adapter = proceduresAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment ProceduresFragment.
         */
        @JvmStatic
        fun newInstance(procList: ProcedureList) =
            ProceduresFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PROC, procList)
                }
            }
    }
}