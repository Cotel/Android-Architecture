package com.cotel.architecture.quotes.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cotel.architecture.quotes.R
import com.cotel.architecture.quotes.presentation.list.QuoteListFragment

class QuotesActivity : AppCompatActivity() {
    companion object {
        private const val FEATURE_FLAG = "FEATURE_FLAG"

        private const val CN_FLAG = "CHUCK_NORRIS"
        private const val PM_FLAG = "PROGRAMMING"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quotes)

        val flag = intent.getStringExtra(FEATURE_FLAG) ?: PM_FLAG

        if (flag == CN_FLAG) {
            injectChuckNorrisFeature()
        } else if (flag == PM_FLAG) {
            injectProgrammingFeature()
        }

        navigateToQuotesList()
    }

    override fun onDestroy() {
        val flag = intent.getStringExtra(FEATURE_FLAG) ?: PM_FLAG

        if (flag == CN_FLAG) {
            ejectChuckNorrisFeature()
        } else if (flag == PM_FLAG) {
            ejectProgrammingFeature()
        }
        super.onDestroy()
    }

    private fun navigateToQuotesList() {
        val fragment = QuoteListFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.quotes_fragment_container, fragment)
            .commit()
    }
}
