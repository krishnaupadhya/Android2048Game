package com.krishna.game2048.utility

import android.app.Application
import android.content.Context
import com.krishna.game2048.di.utilityModule
import com.krishna.game2048.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

/**
 * Application class holds ap level data and initialize library
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        _ctx = this.applicationContext
        startKoinInjection()
    }


    /**
     * init koin and construct dependency graph
     */
    private fun startKoinInjection() {
        startKoin {
            androidContext(this@App)
            modules(getModule())
        }
    }

    /**
     * function to get all di modules array
     */
    @ExperimentalCoroutinesApi
    private fun getModule(): List<Module> {
        return listOf(utilityModule,viewModelModule)
    }

    companion object {

        private lateinit var _ctx: Context

        val ctx: Context
            get() = _ctx
    }
}