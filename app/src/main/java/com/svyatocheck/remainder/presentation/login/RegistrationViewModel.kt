package com.svyatocheck.remainder.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import com.svyatocheck.remainder.domain.usecases.RegistrationUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val registerUseCase : RegistrationUseCase
) : ViewModel() {

    private val _networkingStatus = MutableLiveData<RequestStateStatus>()
    var networkingStatus: LiveData<RequestStateStatus> = _networkingStatus


    fun registration(email: String, password: String, name: String) {
        val handler = CoroutineExceptionHandler { _, exception ->
            _networkingStatus.value = RequestStateStatus.ERROR
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler) {
            val flag = withContext(Dispatchers.IO) {
                _networkingStatus.postValue(RequestStateStatus.LOADING)
                registerUseCase.launchRegistration(email, password, name)
            } ?: false

            if (flag)
                _networkingStatus.value = RequestStateStatus.DONE
            else
                _networkingStatus.value = RequestStateStatus.ERROR
        }
    }


}