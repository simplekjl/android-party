package com.simplekjl.data.client

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Chain): Response {
        var request = chain.request()
        if (request.header("No-Authentication") == null) {
            request = request.newBuilder()
                .addHeader("Authorization", "").build()
        }
        return chain.proceed(request)
    }
}
