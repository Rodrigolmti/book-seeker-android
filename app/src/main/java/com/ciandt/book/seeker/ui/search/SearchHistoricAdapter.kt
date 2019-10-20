package com.ciandt.book.seeker.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ciandt.book.seeker.R
import com.ciandt.book.seeker.domain.model.Historic
import kotlinx.android.synthetic.main.searched_book_item.view.*

class SearchHistoricAdapter : RecyclerView.Adapter<SearchHistoricAdapter.ViewHolder>() {

    private val values: MutableList<Historic> = mutableListOf()
    var onClickListener: ((query: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.searched_book_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    fun updateHistoric(historic: List<Historic>) {
        values.clear()
        values.addAll(historic)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Historic) {
            itemView.tvQuery.text = item.query
            itemView.tvQuery.setOnClickListener {
                onClickListener?.invoke(item.query)
            }
        }
    }
}