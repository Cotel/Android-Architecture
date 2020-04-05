package com.cotel.architecture.base.presentation.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
open class Store<S>(initialState: S) {

    private val scope = MainScope()
    private val store = ConflatedBroadcastChannel<S>(initialState)
    private val actions = Channel<Intent<S>>()

    init {
        scope.launch {
            while (scope.isActive)
                store.offer(actions.receive().reduce(store.value))
        }
    }

    fun latestState(): Flow<S> = store.asFlow()

    suspend fun dispatch(intent: Intent<S>) {
        actions.send(intent)
    }

    fun close() {
        store.close()
        actions.close()
        scope.cancel()
    }

}
