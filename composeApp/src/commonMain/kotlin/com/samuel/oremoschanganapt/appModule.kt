package com.samuel.oremoschanganapt

import com.samuel.oremoschanganapt.presentation.CommonPage.CommonPageViewModel
import com.samuel.oremoschanganapt.presentation.CommonPrays.CommonPraysViewModel
import com.samuel.oremoschanganapt.presentation.Songs.SongsViewModel
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
}