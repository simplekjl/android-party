package com.simplekjl.data.di

import com.simplekjl.data.client.AuthInterceptor
import org.koin.dsl.module

val dataModule = createDataModule()

private fun createDataModule() = module {
    factory { AuthInterceptor(get()) }
}
