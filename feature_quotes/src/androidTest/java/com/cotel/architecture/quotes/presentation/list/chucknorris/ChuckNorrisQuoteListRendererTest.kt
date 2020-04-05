package com.cotel.architecture.quotes.presentation.list.chucknorris

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import arrow.fx.IO
import com.cotel.architecture.base.EmptyActivity
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.domain.model.Quote
import com.cotel.architecture.quotes.domain.repository.QuotesRepository
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import io.mockk.coEvery
import io.mockk.mockkClass
import kotlinx.coroutines.delay
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock

@RunWith(AndroidJUnit4::class)
class ChuckNorrisQuoteListRendererTest : KoinTest {

    @get:Rule
    val mockRule = MockProviderRule.create { clazz -> mockkClass(clazz) }

    @get:Rule
    val activityRule = ActivityTestRule(EmptyActivity::class.java, true, false)

    private val module = module {
        single { ChuckNorrisQuoteListRenderer() }

        viewModel { QuoteListViewModel(repository = get()) }
    }

    @Before
    fun setup() {
        loadKoinModules(module)
    }

    @After
    fun clear() {
        unloadKoinModules(module)
    }

    @Test
    fun if_loading_should_show_chuck_norris_image() {
        val repository = declareMock<QuotesRepository>()

        coEvery { repository.getQuotes(any()) } returns IO.effect {
            delay(3000)
            listOf(Quote("bla", "bla", "bla"))
        }

        startActivity()

        onView(withId(R.id.cn_quotes_loading))
            .check(matches(isDisplayed()))
    }

    private fun startActivity() {
        val fragment = QuoteListFragment.newInstance()
        val activity = activityRule.launchActivity(null)
        activity.setFragment(fragment)
    }
}
