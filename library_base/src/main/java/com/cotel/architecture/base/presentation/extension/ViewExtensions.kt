package com.cotel.architecture.base.presentation.extension

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.clicks(): Flow<Unit> = callbackFlow {
    val listener = View.OnClickListener { safeOffer(Unit) }
    setOnClickListener(listener)
    awaitClose {
        setOnClickListener(null)
    }
}
