package com.sdjic.gradnet.di

import com.sdjic.gradnet.data.local.room.GradNetDB
import com.sdjic.gradnet.data.repo.CryptoRepository
import com.sdjic.gradnet.data.network.source.CoinPagingSource
import com.sdjic.gradnet.data.repo.AuthRepositoryImpl
import com.sdjic.gradnet.data.repo.TestRepositoryImpl
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import com.sdjic.gradnet.di.platform_di.getHttpClient
import com.sdjic.gradnet.di.platform_di.platformModule
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.domain.repo.TestRepository
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreenModel
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreenModel
import com.sdjic.gradnet.presentation.screens.demo.TestViewModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val screenModelsModule = module {
    factory { TestViewModel(testRepository = get()) }
    factory { HomeScreenViewModel(get()) }
    factory { LoginScreenModel(get()) }
    factory { SignUpScreenModel(get()) }
}

val userCases = module {
    single { CoinPagingSource(get()) }
}

val repositoryModule = module {

    single<AuthRepository>{  AuthRepositoryImpl(get())}

    // trying only
    single<TestRepository> { TestRepositoryImpl(testDao = get()) }
    single<CryptoRepository> { CryptoRepository(httpClient = get()) }
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
    single { get<GradNetDB>().testDao }
}

val appModules = listOf(
    platformModule(),
    dataModule,
    repositoryModule,
    userCases,
    screenModelsModule,
    dispatcherModule,
)