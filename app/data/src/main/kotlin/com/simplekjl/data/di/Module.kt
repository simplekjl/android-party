package com.simplekjl.data.di

import com.simplekjl.servers.framework.AuthInterceptor
import com.simplekjl.data.repository.NetworkSource
import com.simplekjl.data.repository.NetworkSourceImpl
import org.koin.dsl.module

val dataModule = createDataModule()

private fun createDataModule() = module {
    factory { com.simplekjl.servers.framework.AuthInterceptor(get()) }
    factory<NetworkSource> { NetworkSourceImpl(get()) }
}
