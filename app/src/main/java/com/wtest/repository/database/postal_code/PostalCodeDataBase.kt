package com.wtest.repository.database.postal_code

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wtest.BuildConfig
import com.wtest.domain.model.PostalCode


/**
 * Database that contains elements.
 */
@Database(
    entities = [PostalCode::class],
    version = BuildConfig.VERSION_CODE
)
abstract class PostalCodeDataBase : RoomDatabase(){
    abstract fun postalCodeDao(): PostalCodeDao
}