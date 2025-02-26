package com.sdjic.gradnet.di

import com.sdjic.gradnet.data.local.preference.AppCacheSettingImpl
import com.sdjic.gradnet.data.local.room.GradNetDB
import com.sdjic.gradnet.data.local.room.TestRepositoryImpl
import com.sdjic.gradnet.data.local.room.UserDataSourceImpl
import com.sdjic.gradnet.data.network.repo.AuthRepositoryImpl
import com.sdjic.gradnet.data.network.repo.CryptoRepository
import com.sdjic.gradnet.data.network.repo.EventRepositoryImpl
import com.sdjic.gradnet.data.network.repo.PostRepositoryImpl
import com.sdjic.gradnet.data.network.repo.UserRepositoryImpl
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import com.sdjic.gradnet.di.platform_di.getHttpClient
import com.sdjic.gradnet.di.platform_di.platformModule
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.domain.repo.EventRepository
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.domain.repo.TestRepository
import com.sdjic.gradnet.domain.repo.UserDataSource
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.domain.useCases.GetPostsUseCase
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpAccountViewModel
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreenModel
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreenModel
import com.sdjic.gradnet.presentation.screens.demo.TestViewModel
import com.sdjic.gradnet.presentation.screens.event.EventScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import com.sdjic.gradnet.presentation.screens.jobs.JobScreenModel
import com.sdjic.gradnet.presentation.screens.posts.AddPostScreenModel
import com.sdjic.gradnet.presentation.screens.posts.PostScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val screenModelsModule = module {
    factory { TestViewModel(testRepository = get()) }
    factory { HomeScreenViewModel(get()) }
    factory { LoginScreenModel(get()) }
    factory { SignUpScreenModel(get()) }
    factory { SetUpAccountViewModel(get(),get(),get()) }
    factory { PostScreenModel(get()) }
    factory { EventScreenModel(get()) }
    factory { AddPostScreenModel(get(),get()) }
    factory { JobScreenModel() }
}

val userCases = module {
    single { GetPostsUseCase(get()) }
}

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserDataSource> { UserDataSourceImpl(get()) }
    single<EventRepository> { EventRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }

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
    single { get<GradNetDB>().userDao }
    single<AppCacheSetting> { AppCacheSettingImpl() }
}

val appModules = listOf(
    platformModule(),
    dataModule,
    repositoryModule,
    userCases,
    screenModelsModule,
    dispatcherModule,
)