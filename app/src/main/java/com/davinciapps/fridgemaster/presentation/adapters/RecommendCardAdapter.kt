package com.davinciapps.fridgemaster.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davinciapps.fridgemaster.R
import com.davinciapps.fridgemaster.data.model.Recipe
import com.davinciapps.fridgemaster.databinding.CardRecommendListBinding
import com.davinciapps.fridgemaster.presentation.viewmodel.BookmarkViewModel

class RecommendCardAdapter(
    var cardTitle: String,
    var recipes: List<Recipe>,
    var viewModel: BookmarkViewModel,
    var showIng: Boolean,
    var loading:Boolean = true
) : RecyclerView.Adapter<RecommendCardAdapter.ViewHolder>() {
    private val TAG = RecommendCardRVAdapter::class.java.simpleName
    private lateinit var context: Context

    private var listener: OnMoreClickListener? = null

    interface OnMoreClickListener {
        fun onMoreClick()
        fun onRefreshClick()
    }

    fun setMoreClickListener(listener: OnMoreClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendCardAdapter.ViewHolder {
        context = parent.context
        val binding = CardRecommendListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: RecommendCardAdapter.ViewHolder, position: Int) {
        holder.bind(cardTitle, context)
    }

    inner class ViewHolder(
        val binding: CardRecommendListBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(cardTitle: String, context: Context) {
            binding.titleTextView.text = cardTitle
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = RecommendCardRVAdapter(recipes, viewModel, showIng)
            }
            binding.loadingBar.visibility = if(loading) View.VISIBLE else View.GONE
            binding.btnRefresh.visibility = if (loading || cardTitle != "기본 레시피") View.GONE else View.VISIBLE
            binding.btnMore.setOnClickListener {
                listener?.onMoreClick()
            }
            binding.btnRefresh.setOnClickListener {
                listener?.onRefreshClick()
            }
        }
    }
}