package com.krishna.game2048.di

import com.krishna.game2048.moudle.game.viewmodel.GameViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { GameViewModel() }
}