package com.wtest.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.wtest.repository.repository.PostalCodeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ListViewModel
@Inject
constructor(
    private val postalCodeRepositoryImpl: PostalCodeRepositoryImpl
) :
    ViewModel() {

    //get only 50 items from the database as Livedata
    val postalCodes = postalCodeRepositoryImpl.loadFirstFiftyItems().asLiveData()

}

