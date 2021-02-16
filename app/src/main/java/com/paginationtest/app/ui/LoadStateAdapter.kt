package com.paginationtest.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.paginationtest.app.databinding.ItemStateLoadingBinding

class BookLoadStateAdapter() : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(
        ItemStateLoadingBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

}