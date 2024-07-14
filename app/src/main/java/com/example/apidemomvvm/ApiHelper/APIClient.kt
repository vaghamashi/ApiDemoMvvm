package com.aneriservices.app.ApiHelper

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object {
        val BASE_URL_LogIn = "https://restcountries.com/v2/"
        val accept = "*/*"
        val userid = "1"
        val entryBy = "reaha"
        val ContentType = "application/json"
        lateinit var retrofit: Retrofit


        private const val CONNECT_TIMEOUT_SECONDS = 10L
        private const val READ_TIMEOUT_SECONDS = 30L

        private val okHttpClient =
            OkHttpClient.Builder().connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS).build()

        fun getApiClientLogin(): Retrofit {
            retrofit = Retrofit.Builder().baseUrl(BASE_URL_LogIn).client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build()

            return retrofit
        }


    }


}