package com.paginationtest.app.networking.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.paginationtest.app.R
import com.paginationtest.app.databinding.ItemBookDataBinding
import com.paginationtest.app.models.Book
import com.paginationtest.app.ui.BookViewHolder

class BookPagingAdapter(diffCallback: DiffUtil.ItemCallback<Book>) :
    PagingDataAdapter<Book, BookViewHolder>(diffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {
        return BookViewHolder(
            ItemBookDataBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBindView(item!!)
    }

    object BookComparator : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            // Id is unique.
            return oldItem.isbn == newItem.isbn
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}