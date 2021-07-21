package com.wtest.presentation.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wtest.repository.repository.PostalCodeRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(
    private val postalCodeRepositoryImpl: PostalCodeRepositoryImpl
) :
    ViewModel() {

    //asks repository to get data from the service
    fun getPostalCodesFromService() {
        postalCodeRepositoryImpl.getPostalCodesFromService()
    }
}