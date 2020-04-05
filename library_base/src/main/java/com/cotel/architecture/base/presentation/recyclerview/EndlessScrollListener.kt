package com.cotel.architecture.base.presentation.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cotel.architecture.base.presentation.extension.safeOffer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class EndlessScrollListener(
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            val layoutManager =
                recyclerView.layoutManager as LinearLayoutManager

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + pastVisibleItems >= totalItemCount)
                onLoadMore()
        }
    }
}


