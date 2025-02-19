package com.sdjic.data.di

import com.sdjic.data.datastore.AppCacheSettingImpl
import com.sdjic.data.local.room.GradNetDB
import com.sdjic.data.network.AuthRepositoryImpl
import com.sdjic.data.network.CryptoRepository
import com.sdjic.data.network.DummyPostRepository
import com.sdjic.data.network.EventRepositoryImpl
import com.sdjic.data.local.TestRepositoryImpl
import com.sdjic.data.network.UserRepositoryImpl
import com.sdjic.domain.AppCacheSetting
import com.sdjic.domain.repo.AuthRepository
import com.sdjic.domain.repo.EventRepository
import com.sdjic.domain.repo.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository>{  AuthRepositoryImpl(get()) }
    single <UserRepository>{ UserRepositoryImpl(get()) }
    single <EventRepository>{ EventRepositoryImpl(get()) }

    single { get<GradNetDB>().testDao }
    single<AppCacheSetting>{ AppCacheSettingImpl()  }

    // trying only
    single<TestRepositoryImpl> { TestRepositoryImpl(testDao = get()) }
    single<CryptoRepository> { CryptoRepository(httpClient = get()) }
    single<DummyPostRepository> { DummyPostRepository(httpClient = get()) }
}
