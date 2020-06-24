package com.cotel.architecture

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_entry.*

class EntryActivity : AppCompatActivity() {

    companion object {
        private const val FEATURE_FLAG = "FEATURE_FLAG"

        private const val CN_FLAG = "CHUCK_NORRIS"
        private const val PM_FLAG = "PROGRAMMING"
    }

    private val splitInstallManager: SplitInstallManager by lazy {
        SplitInstallManagerFactory.create(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        programming_quotes_button.setOnClickListener {
            installQuotesFeature(PM_FLAG)
        }

        chucknorris_quotes_button.setOnClickListener {
            installQuotesFeature(CN_FLAG)
        }
    }

    private fun installQuotesFeature(flag: String) {
        val installRequest = SplitInstallRequest.newBuilder()
            .addModule("feature_quotes")
            .build()

        splitInstallManager.startInstall(installRequest)
            .addOnSuccessListener { navigateToQuotesFeature(flag) }
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

    private fun navigateToQuotesFeature(flag: String) {
        if (splitInstallManager.installedModules.contains("feature_quotes")) {
            val intent = Intent()

            if (flag == PM_FLAG) {
                intent.setClassName(
                    BuildConfig.APPLICATION_ID,
                    "com.cotel.architecture.quotes.presentation.ProgrammingQuotesActivity"
                )
            } else {
                intent.setClassName(
                    BuildConfig.APPLICATION_ID,
                    "com.cotel.architecture.quotes.presentation.ChuckNorrisQuotesActivity"
                )
            }
            startActivity(intent)
        }
    }
}
