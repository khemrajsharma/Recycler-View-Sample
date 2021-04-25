package com.recycyclersample.retrofit

import android.text.TextUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by prakash.meghani on 12/8/2017.
 */
object ApiClient {
    fun getApiInterface(headerAuth: String?): ApiServices {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .client(getHeaderRetro(headerAuth))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiServices::class.java)
    }

    fun getHeaderRetro(headerAuth: String?): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder() //                .connectTimeout(Constant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(
                Interceptor { chain: Interceptor.Chain ->
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                    val token = ""
                    if (!TextUtils.isEmpty(headerAuth)) {
                        if (headerAuth != null) {
                            requestBuilder.addHeader("Authorization", headerAuth)
                        }
                    } else if (!TextUtils.isEmpty(token)) {
                        requestBuilder.addHeader("Authorization", token)
                    }
                    chain.proceed(requestBuilder.build())
                })
            .build()
    }
}