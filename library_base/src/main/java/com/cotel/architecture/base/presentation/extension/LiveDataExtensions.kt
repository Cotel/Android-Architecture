package com.cotel.architecture.base.presentation.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

fun <A, B> LiveData<A>.map(f: (A) -> B): LiveData<B> =
    Transformations.map(this, f)

fun <A, B> LiveData<A>.flatMap(f: (A) -> LiveData<B>): LiveData<B> =
    Transformations.switchMap(this, f)
