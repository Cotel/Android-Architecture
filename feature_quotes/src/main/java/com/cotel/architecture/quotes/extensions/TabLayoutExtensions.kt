package com.cotel.architecture.quotes.extensions

import com.cotel.architecture.base.presentation.extension.safeOffer
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun TabLayout.tabSelections(): Flow<TabLayout.Tab> = callbackFlow {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {}
        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabSelected(tab: TabLayout.Tab) {
            safeOffer(tab)
        }
    }
    addOnTabSelectedListener(listener)
    awaitClose {
        removeOnTabSelectedListener(listener)
    }
}
