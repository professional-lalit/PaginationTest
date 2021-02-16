package com.paginationtest.app.networking

import com.clutchandgear.app.networking.NetworkingInterface
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.paginationtest.app.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import kotlin.math.log

@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Provides
    fun networkApiClient(): OkHttpClient {
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor(logInterceptor)
            .addInterceptor(HeaderInterceptor())
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    /**
     * This class is used to write headers at run time, when APIs are called
     */
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var contentLength = 0L
            if (chain.request().body != null) {
                contentLength = chain.request().body!!.contentLength()
            }
            val requestBuilder: Request.Builder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", contentLength.toString())
            requestBuilder.addHeader(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImhhamFyZS5sYWxpdEBnbWFpbC5jb20iLCJ1c2VySWQiOiI2MDJhYmJlYmExNjEyZDBjZmU4OGIxODUiLCJpYXQiOjE2MTM0ODU1NTQsImV4cCI6MTYxMzQ4OTE1NH0.LczDE-KzRyvYplo2Vj2JOkM9__Zn1liimWwvwGDB5mw"
            )
            return chain.proceed(requestBuilder.build())
        }
    }


    @Provides
    fun retrofit(apiClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(apiClient)
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun networkInterface(retrofit: Retrofit): NetworkingInterface {
        return retrofit.create(NetworkingInterface::class.java)
    }

}