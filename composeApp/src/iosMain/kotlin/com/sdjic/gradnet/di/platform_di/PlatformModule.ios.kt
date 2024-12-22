package com.sdjic.gradnet.di.platform_di

import com.sdjic.gradnet.data.local.room.GradNetDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module{
    single<GradNetDB>{
        IosDatabaseBuilder().build().setQueryCoroutineContext(Dispatchers.IO).build()
    }
}