package com.paginationtest.app.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paginationtest.app.R
import com.paginationtest.app.databinding.ItemBookDataBinding
import com.paginationtest.app.models.Book
import com.paginationtest.app.models.GoogleBook
import java.lang.StringBuilder

class BookViewHolder(private val binding: ItemBookDataBinding) : RecyclerView.ViewHolder(binding.root) {


    fun onBindView(book: Book) {

        if (book.thumbnailUrl?.isNotEmpty() == true) {
            Glide.with(itemView.context).load(book.thumbnailUrl).into(binding.imgBookPoster)
        } else{
            binding.imgBookPoster.setImageResource(R.drawable.placeholder_banner)
        }

        if (book.title?.isNotEmpty() == true) {
            binding.txtBookTitle.text = book.title
        }

        if (book.isbn?.isNotEmpty() == true) {
            binding.txtBookIsbn.text = book.isbn
        }

        binding.txtBookPageCount.text = (book.pageCount ?: 0).toString()

        if (book.publishedDate?.isNotEmpty() == true) {
            binding.txtBookPublishDate.text = book.publishedDate
        }

        if (book.authors?.isNotEmpty() == true) {
            val strAuthor = StringBuilder()
            book.authors.forEach {
                strAuthor.append("$it,")
            }
            binding.txtBookAuthor.text = strAuthor.toString()
        }

    }

}