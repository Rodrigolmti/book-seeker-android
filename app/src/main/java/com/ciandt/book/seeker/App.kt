package com.ciandt.book.seeker

import android.app.Application
import com.ciandt.book.seeker.di.dataModule
import com.ciandt.book.seeker.di.networkModule
import com.ciandt.book.seeker.di.useCaseModule
import com.ciandt.book.seeker.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(dataModule, useCaseModule, networkModule, viewModelModule))
        }
    }
}