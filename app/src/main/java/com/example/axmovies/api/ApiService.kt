package com.example.axmovies.api

import androidx.viewbinding.BuildConfig
import com.example.axmovies.utils.ConstantApp.Api.API_TOKEN
import com.example.axmovies.utils.ConstantApp.Api.API_TOKEN_KEY
import com.example.axmovies.utils.ConstantApp.Api.QUERY_PARAM_LANGUAGE_KEY
import com.example.axmovies.utils.ConstantApp.Api.QUERY_PARAM_LANGUAGE_VALUE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {

    val tmdbApi: TMDBApi = getTMDBApiClient().create(TMDBApi::class.java)

    fun getTMDBApiClient(): Retrofit {
        return Retrofit.Builder()
            //.baseUrl(BuildConfig.BASE_URL)
            .baseUrl("https://api.themoviedb.org/3/")
            .client(getInterceptorClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Manda toda informção da api no log : se esta funcionado ou se tem algum erro
    private fun getInterceptorClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val interceptor = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter(API_TOKEN_KEY, API_TOKEN)
                    .addQueryParameter(QUERY_PARAM_LANGUAGE_KEY, QUERY_PARAM_LANGUAGE_VALUE)
                    .build()
                val newRequest = chain.request().newBuilder().url(url).build()
                chain.proceed(newRequest)}

        return interceptor.build()
   }
}