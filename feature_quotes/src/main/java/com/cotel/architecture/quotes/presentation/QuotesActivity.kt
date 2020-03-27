package com.cotel.architecture.quotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment
import com.cotel.architecture.quotes.presentation.list.ejectFeature
import com.cotel.architecture.quotes.presentation.list.injectFeature

class QuotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        injectFeature()


        navigateToQuotesList()
    }

    override fun onDestroy() {
        ejectFeature()
        super.onDestroy()
    }

    private fun navigateToQuotesList() {
        val fragment = QuoteListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.quotes_fragment_container, fragment)
            .commit()
    }
}
