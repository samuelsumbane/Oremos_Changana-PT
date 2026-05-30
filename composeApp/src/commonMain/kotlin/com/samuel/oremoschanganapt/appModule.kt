package com.samuel.oremoschanganapt

import com.samuel.oremoschanganapt.presentation.CommonPage.CommonPageViewModel
import com.samuel.oremoschanganapt.presentation.commonPrays.CommonPraysViewModel
import com.samuel.oremoschanganapt.presentation.Songs.SongsViewModel
import com.samuel.oremoschanganapt.presentation.lovedData.LovedDataViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

//val networkModule = module {
//    single {
//        HttpClient()
//    }
//}

val appModule = module {
//    includes(
//        repositoryModule,
//        networkModule
//    )

//    single<GameRepository> { QuizGameRepoImpl() }
//    single { SettingsManager(androidContext()) }

    viewModel { CommonPageViewModel() }
    viewModel { SongsViewModel() }
    viewModel { CommonPraysViewModel() }
    viewModel { LovedDataViewModel() }
}