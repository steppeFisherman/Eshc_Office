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
class LateUsersFragmentViewModel @Inject constructor(
    private val fetchUseCase: FetchUseCase,
    private val mapper: MapDomainToUi
) : ViewModel() {

    private var mUsers = MutableLiveData<List<DataUi>>()
    private var mError = MutableLiveData<ErrorType>()

    val users: LiveData<List<DataUi>>
        get() = mUsers
    val error: LiveData<ErrorType>
        get() = mError

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }

    fun fetchData() {
        viewModelScope.launch(exceptionHandler) {
            when (val result = fetchUseCase.fetchLate()) {
                is ResultUser.SuccessLiveData -> {
                    mUsers = result.usersLiveData.map { list ->
                        list.map { dataDomain -> mapper.mapDomainToUi(dataDomain) }
                    } as MutableLiveData<List<DataUi>>
                }

                is ResultUser.Fail -> mError.value = result.errorType
                else -> {}
            }
        }
    }

    init {
//        fetchData()
    }
}