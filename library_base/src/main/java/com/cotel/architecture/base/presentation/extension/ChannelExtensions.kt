package com.cotel.architecture.base.presentation.extension

import kotlinx.coroutines.channels.SendChannel

fun <E> SendChannel<E>.safeOffer(value: E) = !isClosedForSend && try {
    offer(value)
} catch (t: Throwable) {
    // Ignore all
    false
}
