package com.cotel.architecture.quotes.presentation

import android.os.Bundle

class ProgrammingQuotesActivity : QuotesActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        injectProgrammingFeature()
        super.onCreate(savedInstanceState)
    }
}
