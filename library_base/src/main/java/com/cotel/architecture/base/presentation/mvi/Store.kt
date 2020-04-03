package com.cotel.architecture.base.presentation.mvi

import arrow.fx.ForIO
import arrow.fx.IO
import arrow.fx.Ref
import arrow.fx.extensions.io.monad.flatTap
import arrow.fx.extensions.io.monadDefer.monadDefer
import arrow.fx.fix
import kotlinx.coroutines.Dispatchers

typealias Reducer<S, A> = (A, S) -> S
typealias Observer<S> = suspend (S) -> Unit

abstract class Store<S, A>(
    initialState: S,
    private val reducer: Reducer<S, A>,
    private val observer: Observer<S>
) {

    private val state: Ref<ForIO, S> = Ref.unsafe(initialState, IO.monadDefer())

    fun getCurrentState(): IO<S> = state.get().fix()

    suspend fun dispatch(action: A) {
        state
            .updateAndGet { currentState -> reducer(action, currentState) }
            .flatTap { IO.effect(Dispatchers.Main) { observer(it) } }
            .suspended()
    }

}
