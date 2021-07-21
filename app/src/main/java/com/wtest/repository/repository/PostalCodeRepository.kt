package com.wtest.repository.repository

import com.wtest.domain.model.PostalCode
import kotlinx.coroutines.flow.Flow

/**
 * Interface of the PostalCode Repository
 */
interface PostalCodeRepository {

    fun getPostalCodes(): Flow<List<PostalCode>>

    fun insertPostalCodes(postalCodeList: ArrayList<PostalCode>)

    fun getPostalCodesFromService()

    fun searchPostalCode(searchQuery: String): Flow<List<PostalCode>>

    fun loadFirstFiftyItems(): Flow<List<PostalCode>>
}