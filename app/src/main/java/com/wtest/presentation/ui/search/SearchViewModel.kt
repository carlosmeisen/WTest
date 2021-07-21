package com.wtest.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wtest.domain.model.PostalCode
import com.wtest.repository.repository.PostalCodeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val postalCodeRepositoryImpl: PostalCodeRepositoryImpl
) :
    ViewModel() {

    val postalCodes = postalCodeRepositoryImpl.getPostalCodes().asLiveData()

    //get the data from service according to the query
    fun searchPostalCode(searchQuery: String): LiveData<List<PostalCode>> {
        return postalCodeRepositoryImpl.searchPostalCode(searchQuery).asLiveData()
    }

}