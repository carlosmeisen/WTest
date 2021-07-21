package com.wtest.network.services

import retrofit2.http.GET
import java.io.File

interface PostalCodeService {

    //Http Request to retrieve all the postal codes
    @GET("codigos_postais.csv")
    suspend fun getPostalCodes(
    ): String
}