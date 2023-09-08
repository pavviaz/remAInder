package com.svyatocheck.remainder.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svyatocheck.remainder.domain.usecases.AuthorizationUseCase
import com.svyatocheck.remainder.presentation.schedule.utills.RequestStateStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthorizationViewModel(
    private val authUseCase : AuthorizationUseCase
) : ViewModel() {

    private val _networkingStatus = MutableLiveData<RequestStateStatus>()
    var networkingStatus: LiveData<RequestStateStatus> = _networkingStatus

    fun authorization(email: String, password: String) {
        val handler = CoroutineExceptionHandler { _, exception ->
            _networkingStatus.value = RequestStateStatus.DONE
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler) {
            val flag = withContext(Dispatchers.IO) {
                _networkingStatus.postValue(RequestStateStatus.LOADING)
                authUseCase.launchAuthorization(email, password)
            } ?: true

            if (flag)
                _networkingStatus.value = RequestStateStatus.DONE
            else
                _networkingStatus.value = RequestStateStatus.ERROR
        }
    }


}