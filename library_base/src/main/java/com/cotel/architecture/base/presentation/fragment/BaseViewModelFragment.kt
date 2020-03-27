package com.cotel.architecture.base.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.cotel.architecture.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.channels.consumeEach

abstract class BaseViewModelFragment<VS, SE, VM : BaseViewModel<VS, SE>>(
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {

    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(
            viewLifecycleOwner,
            Observer(this::renderViewState)
        )

        lifecycleScope.launchWhenCreated {
            viewModel.sideEffects.consumeEach { handleSideEffect(it) }
        }
    }

    abstract fun renderViewState(viewState: VS)
    abstract fun handleSideEffect(sideEffect: SE)

}
