package com.paginationtest.app.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paginationtest.app.R
import com.paginationtest.app.models.Book
import com.paginationtest.app.models.GoogleBook
import java.lang.StringBuilder

class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var imgBookPoster: ImageView = view.findViewById(R.id.img_book_poster)
    private var txtBookTitle: TextView = view.findViewById(R.id.txt_book_title)
    private var txtBookIsbn: TextView = view.findViewById(R.id.txt_book_isbn)
    private var txtBookPageCount: TextView = view.findViewById(R.id.txt_book_page_count)
    private var txtBookPublishDate: TextView = view.findViewById(R.id.txt_book_publish_date)
    private var txtBookAuthor: TextView = view.findViewById(R.id.txt_book_author)

    fun onBindView(book: Book) {

        if (book.thumbnailUrl?.isNotEmpty() == true) {
            Glide.with(itemView.context).load(book.thumbnailUrl).into(imgBookPoster)
        } else{
            imgBookPoster.setImageResource(R.drawable.placeholder_banner)
        }

        if (book.title?.isNotEmpty() == true) {
            txtBookTitle.text = book.title
        }

        if (book.isbn?.isNotEmpty() == true) {
            txtBookIsbn.text = book.isbn
        }

        txtBookPageCount.text = (book.pageCount ?: 0).toString()

        if (book.publishedDate?.isNotEmpty() == true) {
            txtBookPublishDate.text = book.publishedDate
        }

        if (book.authors?.isNotEmpty() == true) {
            val strAuthor = StringBuilder()
            book.authors.forEach {
                strAuthor.append("$it,")
            }
            txtBookAuthor.text = strAuthor.toString()
        }

    }

}