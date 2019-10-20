package com.ciandt.book.seeker.ui.search_result

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ciandt.book.seeker.R
import com.ciandt.book.seeker.domain.model.Book
import kotlinx.android.synthetic.main.book_item.view.*

class BooksAdapter : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    private val values: MutableList<Book> = mutableListOf()
    var onClickListener: ((book: Book) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.book_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = values.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(values[position])
    }

    fun updateListOfBooks(books: List<Book>) {
        values.clear()
        values.addAll(books)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Book) {
            Glide.with(itemView)
                .load(item.coverImage)
                .centerCrop()
                .into(itemView.ivCover)
            itemView.tvAuthor.text = item.authorName
            itemView.tvTitle.text = item.name
            itemView.setOnClickListener {
                onClickListener?.invoke(item)
            }
        }
    }
}