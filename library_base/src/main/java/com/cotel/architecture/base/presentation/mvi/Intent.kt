package com.cotel.architecture.base.presentation.mvi

interface Intent<S> {
    fun reduce(oldState: S): S
}

fun <S> intent(block: S.() -> S) = BlockIntent(block)

class BlockIntent<S>(val block: S.() -> S) : Intent<S> {
    override fun reduce(oldState: S): S =
        oldState.block()
}

fun <S> sideEffect(block: S.() -> Unit): Intent<S> = object : Intent<S> {
    override fun reduce(oldState: S): S =
        oldState.apply(block)
}
