package com.example.eshccheck.ui.screens

import androidx.lifecycle.*
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

    private var mAllUsers = MutableLiveData<List<DataUi>>()
    private var mUsersAlarmed = MutableLiveData<List<DataUi>>()
    private var mError = MutableLiveData<ErrorType>()

    val allUsers: LiveData<List<DataUi>>
        get() = mAllUsers
    val usersAlarmed: LiveData<List<DataUi>>
        get() = mUsersAlarmed
    val error: LiveData<ErrorType>
        get() = mError

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

     fun fetchAllUsers() {
        viewModelScope.launch(exceptionHandler) {
            when (val result = fetchUseCase.allUsers()) {
                is ResultUser.SuccessList -> {
                    val users = result.users
                    mAllUsers.value = users.map { dataDomain ->
                        mapper.mapDomainToUi(dataDomain)
                    }
                }
                is ResultUser.Fail -> mError.value = result.errorType
                else -> {}
            }
        }
    }

    private fun listenUsers() {
        viewModelScope.launch(exceptionHandler) { fetchUseCase.listenUsers() }
    }

    private fun listenAlarm() {
        viewModelScope.launch(exceptionHandler) {
            when (val result = fetchUseCase.fetchAlarmed()) {
                is ResultUser.SuccessLiveData -> {
                    mUsersAlarmed = result.usersLiveData.map { list ->
                        list.map { dataDomain ->
                            mapper.mapDomainToUi(dataDomain)
                        }
                    } as MutableLiveData<List<DataUi>>
                }
                is ResultUser.Fail -> mError.value = result.errorType
                else -> {}
            }
        }
    }

    init {
        fetchAllUsers()
        listenUsers()
        listenAlarm()
    }
}