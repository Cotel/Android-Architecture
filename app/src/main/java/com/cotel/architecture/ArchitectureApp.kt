package com.cotel.architecture

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat

class ArchitectureApp : Application(), ApplicationComponentProvider {

    override fun onCreate() {
        super.onCreate()
        getApplicationComponent().inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    override fun getApplicationComponent(): ApplicationComponent =
        DaggerApplicationComponent.builder()
            .applicationModule(getApplicationModule())
            .build()

    private fun getApplicationModule(): ApplicationModule =
        ApplicationModule(this)
}
