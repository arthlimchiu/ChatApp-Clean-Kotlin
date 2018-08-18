package com.imakeanapp.chatappkotlin.core

import android.app.Application
import com.imakeanapp.data.core.DatabaseModule
import com.imakeanapp.data.core.RepositoryModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        injector = DaggerAppComponent.builder()
                .databaseModule(DatabaseModule())
                .repositoryModule(RepositoryModule())
                .viewModelModule(ViewModelModule())
                .build()
    }

    companion object {
        private var INSTANCE: App? = null

        fun get(): App = INSTANCE!!
    }
}

lateinit var injector: AppComponent