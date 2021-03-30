package com.example.appwithapi.api

import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class RetrofitClient (){

    companion object {
        const val BASE_URL = "http://192.168.1.12/testApi/public/"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val AUTH = "Basic " + Base64.encodeToString(("Marwa:123").toByteArray(), Base64.NO_WRAP)
    var retrofit: Retrofit? = null


    init {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Authorization", AUTH)
                        .method(original.method(), original.body())

                    val request = requestBuilder.build()
                    return chain.proceed(request)
                }

            }).build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Volatile
    private var INSTANCE: RetrofitClient? = null

    fun getInstance(): RetrofitClient {
        if (INSTANCE == null) {
            synchronized(this) {
                INSTANCE = RetrofitClient()
            }
        }
        return INSTANCE!!
    }

    fun getApi(): Api {
        return retrofit!!.create(Api::class.java)
    }

}