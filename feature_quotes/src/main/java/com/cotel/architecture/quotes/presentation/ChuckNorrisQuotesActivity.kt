package com.cotel.architecture.quotes.presentation

import android.os.Bundle

class ChuckNorrisQuotesActivity : QuotesActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        injectChuckNorrisFeature()
        super.onCreate(savedInstanceState)
    }

}
