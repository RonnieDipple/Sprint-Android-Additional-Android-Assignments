package com.lambdaschool.locationservices.retrofit

import com.lambdaschool.locationservices.model.ContactResults
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface JsonPlaceHolderApi {

    // TODO: S09M02-7 update path to return location data
    @GET("api/")
    fun getContacts(
        @Query("format") format: String = "json",
        @Query("results") results: Int
    ): Call<ContactResults>

    class Factory {

        companion object {

            private const val BASE_URL = "https://randomuser.me/"

            fun create(): JsonPlaceHolderApi {

                val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.NONE
//                logger.level = HttpLoggingInterceptor.Level.BASIC
//                logger.level = HttpLoggingInterceptor.Level.BODY

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logger)
                    .retryOnConnectionFailure(false)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(JsonPlaceHolderApi::class.java)
            }
        }
    }
}