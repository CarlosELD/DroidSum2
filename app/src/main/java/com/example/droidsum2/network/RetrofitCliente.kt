package com.example.droidsum2.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitCliente {
    private const val BASE_URL = "https://sicenet.surguanajuato.tecnm.mx/"
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .build()
            chain.proceed(request)
        })
    }.build()

    @Suppress("DEPRECATION")
    val instance: SumService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        retrofit.create(SumService::class.java)
    }
}
