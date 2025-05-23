package com.sdjic.gradnet.di.platform_di

import com.sdjic.gradnet.domain.location.LocationRepository
import com.sdjic.gradnet.GradNetApp
import com.sdjic.gradnet.data.local.room.GradNetDB
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<GradNetDB> {
        AndroidDatabaseBuilder(get()).build().setQueryCoroutineContext(Dispatchers.IO).build()
    }
    factory<LocationRepository> { AndroidLocationRepository(context = GradNetApp.AppContext) }
}