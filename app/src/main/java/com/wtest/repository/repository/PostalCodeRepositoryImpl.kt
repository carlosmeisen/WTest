package com.wtest.repository.repository

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import com.wtest.domain.model.PostalCode
import com.wtest.network.services.PostalCodeService
import com.wtest.repository.database.postal_code.PostalCodeDao
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

/**
 * Implementation of the Postal Code Repository
 */
class PostalCodeRepositoryImpl
@Inject
constructor(
    private val postalCodeService: PostalCodeService,
    private val postalCodeDao: PostalCodeDao
) : PostalCodeRepository {

    //Exception handling
    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        error.message?.let { Log.i("repositoryCoroutine", it) }
    }

    //gets all postal codes from database
    override fun getPostalCodes(): Flow<List<PostalCode>> =
        postalCodeDao.loadAllPostalCodes()

    //gets data via HTTP through service
    override fun getPostalCodesFromService() {
        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            val resultAsync = async {
                postalCodeService.getPostalCodes()
            }
            val result = resultAsync.await()
            handleGetPostalCodesFromService(result)
        }
    }

    //gets data from data base based on a query
    override fun searchPostalCode(searchQuery: String): Flow<List<PostalCode>> {
        return postalCodeDao.searchPostalCode(searchQuery)
    }

    //gets the first 50 items from the database
    override fun loadFirstFiftyItems(): Flow<List<PostalCode>> {
        return postalCodeDao.loadFirstFiftyItems()
    }

    //insert data into the database
    override fun insertPostalCodes(postalCodeList: ArrayList<PostalCode>) {
        CoroutineScope(Dispatchers.IO).launch {
            postalCodeDao.insertAll(postalCodeList)
        }
    }

    //handles the data got from the service
    //Since the result of the HTTP GET is a String, had to do a split by ",".
    //had to add the index value variable to ignore the first line of result (header)
    private fun handleGetPostalCodesFromService(result: String) {
        val postalCodeList = ArrayList<PostalCode>()
        var index = 0

        result.lines().forEach { line ->
            if (index > 0) {
                var row = line.split(",")
                if (row.count() > 13) {
                    val nPostalCode = PostalCode()
                    nPostalCode.postalCodeNum = row[14]
                    nPostalCode.postalCodeExt = row[15]
                    nPostalCode.postalDesignation = row[16]
                    postalCodeList.add(nPostalCode)
                }
            } else index = 1
        }
        insertPostalCodes(postalCodeList)
    }
}