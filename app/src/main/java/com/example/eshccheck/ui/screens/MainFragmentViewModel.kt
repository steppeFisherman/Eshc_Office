package com.example.eshccheck.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eshccheck.domain.FetchUseCase
import com.example.eshccheck.domain.model.ErrorType
import com.example.eshccheck.domain.model.ResultUser
import com.example.eshccheck.ui.model.DataUi
import com.example.eshccheck.ui.model.MapDomainToUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val fetchUseCase: FetchUseCase,
    private val mapper: MapDomainToUi
) : ViewModel() {

    private var mUsers = MutableLiveData<List<DataUi>>()
    private var mError = MutableLiveData<ErrorType>()
    private var mLoading = MutableLiveData<ResultUser.Loading>()

    val users: LiveData<List<DataUi>>
        get() = mUsers
    val error: LiveData<ErrorType>
        get() = mError
    val loading: LiveData<ResultUser.Loading>
        get() = mLoading

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    private fun fetchAllUsers() {
        viewModelScope.launch(exceptionHandler) {
            when (val result = fetchUseCase.allUsers()) {
                is ResultUser.Success -> {


                    val users1 = result.users
                    mUsers.value = users1.map { dataDomain ->
                        mapper.mapDomainToUi(dataDomain)
                    }
                }
                is ResultUser.Fail -> mError.value = result.errorType
                else -> {}
            }
        }
    }

    init {
        fetchAllUsers()
    }
}