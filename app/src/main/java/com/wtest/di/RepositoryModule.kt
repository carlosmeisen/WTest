package com.wtest.di

import com.wtest.network.services.PostalCodeService
import com.wtest.repository.database.postal_code.PostalCodeDao
import com.wtest.repository.repository.PostalCodeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //creating a single instance of the repository and setting up its injection

    @Singleton
    @Provides
    fun providePostalCodeRepository(postalCodeService: PostalCodeService, postalCodeDao: PostalCodeDao) =
        PostalCodeRepositoryImpl(postalCodeService, postalCodeDao)
}
