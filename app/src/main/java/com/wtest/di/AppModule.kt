package com.wtest.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.wtest.MainApplication
import com.wtest.repository.database.postal_code.PostalCodeDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //creating a single instance of the data base and dao. Also, setting up their injection

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Provides
    @Singleton
    fun providePostalCodeDataBase(@ApplicationContext appContext: Context) = Room.databaseBuilder(
        appContext,
        PostalCodeDataBase::class.java,
        "postalcode.db"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun providePostalCodeDao(db: PostalCodeDataBase) = db.postalCodeDao()
}