package com.paginationtest.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paginationtest.app.R
import com.paginationtest.app.models.Book
import com.paginationtest.app.models.GoogleBook

//class BookAdapter(private val list: List<Book>) : RecyclerView.Adapter<BookViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
//        return BookViewHolder(
//            LayoutInflater.from(parent.context).inflate(R.layout.item_book_data, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
//        holder.onBindView(list[position])
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//}