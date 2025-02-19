package com.sdjic.gradnet.di

import com.sdjic.data.di.repositoryModule
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import com.sdjic.gradnet.di.platform_di.getHttpClient
import com.sdjic.gradnet.di.platform_di.platformModule
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpAccountViewModel
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreenModel
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreenModel
import com.sdjic.gradnet.presentation.screens.demo.TestViewModel
import com.sdjic.event.EventScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import com.sdjic.jobs.JobScreenModel
import com.sdjic.posts.PostScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val screenModelsModule = module {
    factory { HomeScreenViewModel() }
    factory { LoginScreenModel(get()) }
    factory { SignUpScreenModel(get()) }
    factory { SetUpAccountViewModel(get()) }
    factory { PostScreenModel(get()) }
    factory { EventScreenModel(get()) }
    factory { JobScreenModel(get()) }

    factory { TestViewModel(testRepository = get()) }
}

val userCases = module {
}


val dispatcherModule = module {
    single { Dispatchers.IO }
    single { Dispatchers.Default }
    single { Dispatchers.Main }
    single { Dispatchers.Unconfined }
}

val dataModule = module {
    single { getHttpClient() }
    single {
        getDatabaseBuilder().build().setQueryCoroutineContext(Dispatchers.IO).build()
    }
}

val appModules = listOf(
    platformModule(),
    dataModule,
    repositoryModule,
    userCases,
    screenModelsModule,
    dispatcherModule,
)