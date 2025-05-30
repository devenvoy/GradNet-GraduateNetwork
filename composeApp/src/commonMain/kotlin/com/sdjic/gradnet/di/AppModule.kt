package com.sdjic.gradnet.di

import com.sdjic.gradnet.data.local.preference.AppCacheSettingImpl
import com.sdjic.gradnet.data.local.room.GradNetDB
import com.sdjic.gradnet.data.local.room.repo.TestRepositoryImpl
import com.sdjic.gradnet.data.local.room.repo.UserDataSourceImpl
import com.sdjic.gradnet.data.network.repo.AuthRepositoryImpl
import com.sdjic.gradnet.data.network.repo.GeneralRepository
import com.sdjic.gradnet.data.network.repo.EventRepositoryImpl
import com.sdjic.gradnet.data.network.repo.JobsRepositoryImpl
import com.sdjic.gradnet.data.network.repo.PostRepositoryImpl
import com.sdjic.gradnet.data.network.repo.UserRepositoryImpl
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import com.sdjic.gradnet.di.platform_di.getHttpClient
import com.sdjic.gradnet.di.platform_di.platformModule
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.domain.repo.AuthRepository
import com.sdjic.gradnet.domain.repo.EventRepository
import com.sdjic.gradnet.domain.repo.JobsRepository
import com.sdjic.gradnet.domain.repo.PostRepository
import com.sdjic.gradnet.domain.repo.TestRepository
import com.sdjic.gradnet.domain.repo.UserDataSource
import com.sdjic.gradnet.domain.repo.UserRepository
import com.sdjic.gradnet.domain.useCases.GetJobsUseCase
import com.sdjic.gradnet.domain.useCases.GetLikedPostsUseCase
import com.sdjic.gradnet.domain.useCases.GetLostItemsUseCase
import com.sdjic.gradnet.domain.useCases.GetPostsUseCase
import com.sdjic.gradnet.domain.useCases.GetSavedJobUseCase
import com.sdjic.gradnet.domain.useCases.GetUsersUseCase
import com.sdjic.gradnet.domain.useCases.LikePostUseCase
import com.sdjic.gradnet.presentation.screens.accountSetup.SetUpAccountViewModel
import com.sdjic.gradnet.presentation.screens.auth.login.LoginScreenModel
import com.sdjic.gradnet.presentation.screens.auth.password.ChangePasswordScreenModel
import com.sdjic.gradnet.presentation.screens.auth.password.ForgotPasswordScreenModel
import com.sdjic.gradnet.presentation.screens.auth.register.SignUpScreenModel
import com.sdjic.gradnet.presentation.screens.demo.TestViewModel
import com.sdjic.gradnet.presentation.screens.event.EventScreenModel
import com.sdjic.gradnet.presentation.screens.home.HomeScreenViewModel
import com.sdjic.gradnet.presentation.screens.jobs.JobDetailScreenModel
import com.sdjic.gradnet.presentation.screens.jobs.JobScreenModel
import com.sdjic.gradnet.presentation.screens.jobs.SavedJobScreenModel
import com.sdjic.gradnet.presentation.screens.posts.AddPostScreenModel
import com.sdjic.gradnet.presentation.screens.posts.LikedPostScreenModel
import com.sdjic.gradnet.presentation.screens.posts.PostScreenModel
import com.sdjic.gradnet.presentation.screens.profile.ProfileScreenModel
import com.sdjic.gradnet.presentation.screens.search.SearchScreenModel
import com.sdjic.gradnet.presentation.screens.setting.lost_found.LostItemListScreenModel
import com.sdjic.gradnet.presentation.screens.setting.lost_found.LostItemReportScreenModel
import com.sdjic.gradnet.presentation.screens.splash.SplashScreenModel
import com.sdjic.gradnet.presentation.screens.verification.UserVerificationScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val screenModelsModule = module {

    factory { SplashScreenModel(get(), get()) }

    // auth
    factory { LoginScreenModel(get()) }
    factory { SignUpScreenModel(get()) }
    factory { ForgotPasswordScreenModel(get()) }
    factory { ChangePasswordScreenModel(get(), get()) }
    factory { UserVerificationScreenModel(get(), get(), get()) }

    // home , profile , post , jobs
    factory { HomeScreenViewModel(get(), get()) }
    factory { ProfileScreenModel(get(), get(), get()) }
    factory { SetUpAccountViewModel(get(), get(), get()) }

    factory { JobScreenModel(get()) }
    factory { EventScreenModel(get()) }
    factory { SearchScreenModel(get()) }
    factory { JobDetailScreenModel(get(), get()) }
    factory { LostItemListScreenModel(get()) }
    factory { AddPostScreenModel(get(), get(), get()) }
    factory { PostScreenModel(get(), get(), get()) }
    factory { LikedPostScreenModel(get(), get(), get()) }
    factory { SavedJobScreenModel(get(), get(), get()) }
    factory { LostItemReportScreenModel(get(),get()) }

    // testing only
    factory { TestViewModel(testRepository = get()) }
}

val userCases = module {
    single { GetUsersUseCase(get()) }
    single { LikePostUseCase(get()) }
    single { GetSavedJobUseCase(get()) }
    single { GetLostItemsUseCase(get()) }
    single { GetLikedPostsUseCase(get()) }
    single { GetJobsUseCase(get(), get()) }
    single { GetPostsUseCase(get(), get()) }
}

val repositoryModule = module {

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<UserDataSource> { UserDataSourceImpl(get()) }
    single<EventRepository> { EventRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get()) }
    single<JobsRepository> { JobsRepositoryImpl(get()) }
    single<GeneralRepository> { GeneralRepository(get()) }

    // testing only
    single<TestRepository> { TestRepositoryImpl(testDao = get()) }
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
    single { get<GradNetDB>().postDao }
    single { get<GradNetDB>().postRemoteKeysDao }
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