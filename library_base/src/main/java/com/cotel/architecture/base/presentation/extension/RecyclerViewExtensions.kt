package com.cotel.architecture.base.presentation.extension

import androidx.recyclerview.widget.RecyclerView
import com.cotel.architecture.base.presentation.recyclerview.EndlessScrollListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun RecyclerView.endlessScroll(): Flow<Unit> = callbackFlow {
    val endlessScrollListener = EndlessScrollListener { safeOffer(Unit) }
    addOnScrollListener(endlessScrollListener)
    awaitClose {
        removeOnScrollListener(endlessScrollListener)
    }
}
