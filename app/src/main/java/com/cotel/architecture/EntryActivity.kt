package com.cotel.architecture

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class EntryActivity : AppCompatActivity() {

    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        installQuotesFeature()
    }

    private fun installQuotesFeature() {
        val installRequest = SplitInstallRequest.newBuilder()
            .addModule("feature_quotes")
            .build()

        splitInstallManager.startInstall(installRequest)
            .addOnSuccessListener { navigateToQuotesFeature() }
            .addOnFailureListener { showInstallQuotesFeatureError() }
    }

    private fun showInstallQuotesFeatureError() {
        Toast.makeText(
            this,
            "Fail installing Quotes Feature",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun navigateToQuotesFeature() {
        if (splitInstallManager.installedModules.contains("feature_quotes")) {
            val intent = Intent()
            intent.setClassName(
                BuildConfig.APPLICATION_ID,
                "com.cotel.architecture.quotes.presentation.QuotesActivity"
            )
            startActivity(intent)
            finish()
        }
    }
}
