package com.simplekjl.data.di

import com.simplekjl.data.repository.NetworkSource
import com.simplekjl.data.repository.NetworkSourceImpl
import org.koin.dsl.module

val dataModule = createDataModule()

private fun createDataModule() = module {
    factory<NetworkSource> { NetworkSourceImpl(get()) }
}
