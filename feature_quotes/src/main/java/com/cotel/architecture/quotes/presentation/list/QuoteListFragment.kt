package com.cotel.architecture.quotes.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.cotel.architecture.quotes.domain.store.QuoteListStore
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.scope.viewModel
import org.koin.androidx.scope.lifecycleScope as koinScope

class QuoteListFragment : Fragment() {

    companion object {
        fun newInstance() = QuoteListFragment()
    }

    private val viewModel: QuoteListViewModel by koinScope.viewModel(this)
    private val renderer: QuoteListRenderer by koinScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(renderer.getLayoutRes(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderer.setup(this)

        renderer.actions()
            .onEach { viewModel.handleViewAction(it) }
            .launchIn(lifecycleScope)

        viewModel.viewState
            .onEach { renderViewState(it) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            viewModel.handleViewAction(QuoteListViewActions.StartScreen)
        }

    }

    private fun renderViewState(viewState: QuoteListViewState) {
        renderer.renderState(viewState)
    }

    private fun showErrorSaveQuoteToast() {
        Toast.makeText(
            requireContext(),
            "Fail while saving quote",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showErrorRemoveQuoteToast() {
        Toast.makeText(
            requireContext(),
            "Fail while removing quote",
            Toast.LENGTH_SHORT
        ).show()
    }
}
