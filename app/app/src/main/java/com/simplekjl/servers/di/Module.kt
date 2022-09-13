package com.simplekjl.servers.di

import android.annotation.SuppressLint
import com.simplekjl.servers.BuildConfig
import com.simplekjl.servers.storage.SessionManagerImpl
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = createMainModule() // UI level


@SuppressLint("UnspecifiedImmutableFlag")
private fun createMainModule() = module {
    single {
        val builder = OkHttpClient().newBuilder()
        builder.readTimeout(0, TimeUnit.SECONDS)
        builder.connectTimeout(5, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            builder.addInterceptor(interceptor)
        }

        val client = builder.build()

        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
    single { SessionManagerImpl(androidContext()) }
}
