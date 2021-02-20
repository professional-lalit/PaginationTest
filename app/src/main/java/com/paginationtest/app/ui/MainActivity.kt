package com.paginationtest.app.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paginationtest.app.R
import com.paginationtest.app.databinding.ActivityMainBinding
import com.paginationtest.app.models.Book
import com.paginationtest.app.networking.paging.BookPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        loadBooks()
    }

    private fun setAdapter() {
        binding.recyclerBooks.adapter = mainViewModel.bookPagingAdapter.withLoadStateFooter(BookLoadStateAdapter())
        binding.recyclerBooks.layoutManager = LinearLayoutManager(this)
    }

    private fun loadBooks() {
        mainViewModel.getBooks()
    }
}