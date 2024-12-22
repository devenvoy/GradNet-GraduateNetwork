package com.sdjic.gradnet.di

import androidx.compose.ui.input.key.Key.Companion.I
import androidx.room.RoomDatabase
import com.sdjic.gradnet.data.local.room.GradNetDB
import com.sdjic.gradnet.data.repo.TestRepositoryImpl
import com.sdjic.gradnet.di.platform_di.getDatabaseBuilder
import com.sdjic.gradnet.di.platform_di.getHttpClient
import com.sdjic.gradnet.di.platform_di.platformModule
import com.sdjic.gradnet.domain.repo.TestRepository
import com.sdjic.gradnet.presentation.screens.demo.TestViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            platformModule(),
            dataModule,
            repositoryModule,
            screenModelsModule,
            dispatcherModule,
        )
    }


val screenModelsModule = module {
    factory { TestViewModel(testRepository = TestRepositoryImpl(testDao = get())) }
//    factory { DetailScreenModel(museumRepository = get()) }
//    factory { QuestionScreenModel(questionDataSource = get()) }
}

val repositoryModule = module {
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
//    single { getDatabaseBuilder() }
//    single {
//        get<RoomDatabase.Builder<GradNetDB>>().setQueryCoroutineContext(Dispatchers.IO).build()
//    }
    single { get<GradNetDB>().testDao }
}