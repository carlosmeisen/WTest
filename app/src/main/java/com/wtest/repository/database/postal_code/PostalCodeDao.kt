package com.wtest.repository.database.postal_code

import androidx.room.*
import com.wtest.domain.model.PostalCode
import kotlinx.coroutines.flow.Flow

/**
 * Postal Code DAO (Data Access Object).
 */
@Dao
interface PostalCodeDao {

    @Transaction
    @Query("SELECT * FROM postalcode_table")
    fun loadAllPostalCodes(): Flow<List<PostalCode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(objects: List<PostalCode>): List<Long>

    @Query("SELECT * FROM postalcode_table WHERE postalCodeNum LIKE :searchQuery OR postalCodeExt LIKE :searchQuery OR postalDesignation LIKE :searchQuery")
    fun searchPostalCode(searchQuery: String): Flow<List<PostalCode>>

    @Query("SELECT * FROM postalcode_table WHERE id BETWEEN 1 AND 51")
    fun loadFirstFiftyItems(): Flow<List<PostalCode>>
}
