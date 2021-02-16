package com.paginationtest.app.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paginationtest.app.R
import com.paginationtest.app.databinding.ActivityMainBinding
import com.paginationtest.app.models.Book
import com.paginationtest.app.networking.paging.BookPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bookPagingAdapter: BookPagingAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addErrorObserver()
        setAdapter()
        loadBooks()
    }

    private fun addErrorObserver() {
        mainViewModel.errorData.observe(this, {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setAdapter() {
        bookPagingAdapter = BookPagingAdapter(BookPagingAdapter.BookComparator)
        bookPagingAdapter.withLoadStateFooter(
            footer = BookLoadStateAdapter()
        )
        binding.recyclerBooks.adapter = bookPagingAdapter
        binding.recyclerBooks.layoutManager = LinearLayoutManager(this)
    }

    private fun loadBooks() {
        GlobalScope.launch {
            mainViewModel.flow.collectLatest { pagingData ->
                bookPagingAdapter.submitData(pagingData)
            }
        }
    }
}