package com.paginationtest.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.paginationtest.app.R
import com.paginationtest.app.databinding.ItemStateLoadingBinding

class LoadStateViewHolder(val binding: ItemStateLoadingBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.txtLoadMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = true
        binding.txtLoadMsg.isVisible = true
    }

}