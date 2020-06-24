package com.cotel.architecture

import android.app.Application
import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun context(): Context

    fun inject(application: ArchitectureApp)
}

@Module
class ApplicationModule(
    val appContext: Context
) {
    @Singleton
    @Provides
    fun providesApplicationContext(): Context = appContext
}
