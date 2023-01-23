package com.example.everyrecipe.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.everyrecipe.data.model.Procedure
import com.example.everyrecipe.databinding.ProcedureListItemBinding

class ProceduresAdapter(
    var procedures: List<Procedure>
) : RecyclerView.Adapter<ProceduresAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ProcedureListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(procedures[position])
    }

    override fun getItemCount(): Int {
        return procedures.size
    }

    inner class ViewHolder(
        val binding: ProcedureListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(procedure: Procedure) {
            binding.tvTitle.text = "${procedure.COOKING_NO}. ${procedure.COOKING_DC}"
        }
    }
}