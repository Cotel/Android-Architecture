package com.cotel.architecture.base.presentation.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.cotel.architecture.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.channels.consumeEach

abstract class BaseViewModelActivity<VS, SE, VM : BaseViewModel<VS, SE>> :
    AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResId: Int

    protected abstract val viewModel: BaseViewModel<VS, SE>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        viewModel.viewState.observe(this, Observer(this::renderViewState))

        lifecycleScope.launchWhenCreated {
            viewModel.sideEffects.consumeEach(this@BaseViewModelActivity::handleSideEffect)
        }
    }

    protected abstract fun renderViewState(viewState: VS)
    protected abstract fun handleSideEffect(sideEffect: SE)
}
