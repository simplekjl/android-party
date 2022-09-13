package com.simplekjl.domain.di

import com.simplekjl.domain.usecase.GetAllServersUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val domainModule = module {
    factory { GetAllServersUseCase(Dispatchers.IO, get()) }
}
