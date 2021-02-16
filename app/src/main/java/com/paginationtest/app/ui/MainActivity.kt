package com.paginationtest.app.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paginationtest.app.R
import com.paginationtest.app.models.Book
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var recyclerBooks: RecyclerView
    private lateinit var pgBar: ProgressBar

    private lateinit var adapter: BookAdapter
    private val bookList = ArrayList<Book>()

    private val mainViewModel: MainViewModel by viewModels()
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addErrorObserver()
        addBookResponseObserver()
        initViews()
        setAdapter()
        loadBooks()
    }

    private fun addErrorObserver() {
        mainViewModel.errorData.observe(this, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun addBookResponseObserver() {
        mainViewModel.bookList.observe(this, { list ->
            loading = false
            pgBar.visibility = View.GONE
            bookList.addAll(list)
            adapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        recyclerBooks = findViewById(R.id.recycler_books)
        pgBar = findViewById(R.id.pgbar)
    }

    private fun setAdapter() {
        adapter = BookAdapter(bookList)
        recyclerBooks.adapter = adapter
        recyclerBooks.layoutManager = LinearLayoutManager(this)
        recyclerBooks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    val visibleItemCount = recyclerBooks.layoutManager?.childCount
                    val totalItemCount = recyclerBooks.layoutManager?.itemCount
                    val pastVisiblesItems = (recyclerBooks.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                    if (!loading) {
                        if (visibleItemCount!! + pastVisiblesItems >= totalItemCount!!) {
                            loadBooks()
                        }
                    }
                }
            }
        })
    }

    private fun loadBooks() {
        pgBar.visibility = View.VISIBLE
        mainViewModel.getBooks()
        loading = true
    }
}