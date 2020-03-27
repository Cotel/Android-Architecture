package com.cotel.architecture.quotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.presentation.list.*

class QuotesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)
        injectChuckNorrisFeature()


        navigateToQuotesList()
    }

    override fun onDestroy() {
        ejectChuckNorrisFeature()
        super.onDestroy()
    }

    private fun navigateToQuotesList() {
        val fragment = QuoteListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.quotes_fragment_container, fragment)
            .commit()
    }
}
