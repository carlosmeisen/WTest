package com.wtest.di

import com.wtest.BuildConfig
import com.wtest.network.services.PostalCodeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    //creating a single instance of the service to get the postal codes data and setting up its injection

    @Singleton
    @Provides
    fun providesPostalCodeService(): PostalCodeService {

        return Retrofit.Builder()
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.create()
            )
            .addConverterFactory(
                ScalarsConverterFactory.create()
            )
            .client(OkHttpClient())
            .baseUrl(BuildConfig.POSTAL_CODE_API_URL)
            .build()
            .create(PostalCodeService::class.java)
    }
}