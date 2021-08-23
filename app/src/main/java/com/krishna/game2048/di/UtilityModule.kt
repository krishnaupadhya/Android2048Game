package com.krishna.game2048.di

import com.krishna.game2048.utility.App
import com.krishna.game2048.utility.sharedpreference.IPreferenceHelper
import com.krishna.game2048.utility.sharedpreference.SharedPreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val utilityModule = module(createdAtStart = true) {
    //Provide shared preference manager singleton instance
    single<IPreferenceHelper> {
        SharedPreferenceManager(androidApplication() as App)
    }

}