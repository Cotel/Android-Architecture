package com.cotel.architecture

import android.app.Application
import android.content.Context
import com.google.android.play.core.splitcompat.SplitCompat
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ArchitectureApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@ArchitectureApp)
            if (BuildConfig.DEBUG) androidLogger()
        }
    }

}
