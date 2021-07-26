package com.dyippay.appmodules

import android.content.Context
import com.dyippay.data.services.ItemService
import com.dyippay.BuildConfig
import com.dyippay.CACHE_SIZE
import com.dyippay.OKHTTP_CONNECT_TIMEOUT_SECONDS
import com.dyippay.OKHTTP_READ_TIMEOUT_SECONDS
import com.dyippay.OKHTTP_WRITE_TIMEOUT_SECONDS
import com.dyippay.network.ServiceInterceptor
import com.dyippay.util.getPref
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModules {

    @Provides
    @Singleton
    fun provideServiceInterceptor(@ApplicationContext context: Context) = ServiceInterceptor(context.getPref())

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context) =
        Cache(File(context.cacheDir, "http-cache"), CACHE_SIZE)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        cache: Cache,
        @ApplicationContext context: Context
    ) =
        OkHttpClient.Builder().apply {
            cache(cache)
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                addInterceptor(ChuckerInterceptor(context))
            }
            addInterceptor(ServiceInterceptor(context.getPref()))
            connectTimeout(OKHTTP_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            readTimeout(OKHTTP_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(OKHTTP_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideSearchService(retrofit: Retrofit) =
        retrofit.create(ItemService::class.java)
}
