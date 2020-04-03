package com.cotel.architecture.quotes.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import arrow.fx.IO
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuoteListViewModelTest {

    @get:Rule
    val testCoroutineRule = InstantTaskExecutorRule()

    val testCoroutineContext = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineContext)
    }

    @After
    fun clean() {
        Dispatchers.resetMain()
        testCoroutineContext.cleanupTestCoroutines()
    }

    @Test
    fun `loading quotes shows loading view state once`() {
        val repository = mockk<QuotesRepository>()

        var loadingCounter = 0
        val observer = Observer<QuoteListViewModel.ViewState> {
            if (it == QuoteListViewModel.ViewState.Loading)
                loadingCounter++
        }

        coEvery { repository.getQuotes(any()) } returns
            IO.just(listOf(Quote("Bla", "bla bla", "Blabber")))

        val viewModel = QuoteListViewModel(repository)
        viewModel.loadAllQuotes(true)

        viewModel.viewState.observeForever(observer)
        viewModel.loadAllQuotes()

        assertThat(loadingCounter, `is`(1))
    }

    @Test
    fun `loading quotes success shows content loaded view state`() {
        val repository = mockk<QuotesRepository>()
        val observer =
            mockk<Observer<QuoteListViewModel.ViewState>>(relaxed = true)

        val fakeData = listOf(
            Quote("Bla", "bla bla", "Blabber"),
            Quote("Bla2", "bla bla", "Blabber", true)
        )
        coEvery { repository.getQuotes(any()) } returns IO.just(fakeData)

        val viewModel = QuoteListViewModel(repository)

        viewModel.viewState.observeForever(observer)

        verify(exactly = 1) {
            observer
                .onChanged(QuoteListViewModel.ViewState.DataLoaded(fakeData))
        }

        confirmVerified(observer)
    }

    @Test
    fun `loading quotes error shows error view state once`() {
        val repository = mockk<QuotesRepository>()
        val observer =
            mockk<Observer<QuoteListViewModel.ViewState>>(relaxed = true)

        coEvery { repository.getQuotes(any()) } returns
            IO.raiseError(Exception())

        val viewModel = QuoteListViewModel(repository)

        viewModel.viewState.observeForever(observer)

        verify(exactly = 1) {
            observer.onChanged(QuoteListViewModel.ViewState.Error)
        }

        confirmVerified(observer)
    }

    @Test
    fun `changing filter should show content loaded with only saved`() {
        val repository = mockk<QuotesRepository>()
        val observer =
            mockk<Observer<QuoteListViewModel.ViewState>>(relaxed = true)

        val fakeData = listOf(
            Quote("Bla", "bla bla", "Blabber"),
            Quote("Bla2", "bla bla", "Blabber", true)
        )
        val filteredData = fakeData.filter { it.isSaved }
        coEvery { repository.getQuotes(any()) } returns IO.just(fakeData)
        coEvery { repository.getSavedQuotes() } returns IO.just(filteredData)

        val viewModel = QuoteListViewModel(repository)
        viewModel.handleSavedFilterPressed()

        viewModel.viewState.observeForever(observer)

        verify(exactly = 1) {
            observer.onChanged(
                QuoteListViewModel
                    .ViewState
                    .DataLoaded(filteredData)
            )
        }
        confirmVerified(observer)

    }

}