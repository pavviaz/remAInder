package com.svyatocheck.remainder.presentation.recorder

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import com.svyatocheck.remainder.domain.usecases.SendAudioToRemoteUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecorderViewModel(
    private val audioSender : SendAudioToRemoteUseCase
) : ViewModel() {

    private val _networkingStatus = MutableLiveData<RequestStateStatus>()
    var networkingStatus: LiveData<RequestStateStatus> = _networkingStatus

    // creating a variable for media recorder object class.
    private var mRecorder: WavRecorder? = null

    private fun sendAudio(audioPath: String?) {
        val handler = CoroutineExceptionHandler { _, exception ->
            _networkingStatus.value = RequestStateStatus.ERROR
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler) {
            val flag = withContext(Dispatchers.IO) {
                if (audioPath == null)
                    throw Exception("Empty wav file.")

                _networkingStatus.postValue(RequestStateStatus.LOADING)
                audioSender.sendAudio(audioPath)
            }

            if (!flag)
                _networkingStatus.value = RequestStateStatus.ERROR
            else
                _networkingStatus.value = RequestStateStatus.DONE
        }
    }

    @SuppressLint("MissingPermission")
    fun startRecording(filepath: String) {
        mRecorder = WavRecorder(filepath)
        mRecorder!!.startRecording()
    }

    fun stopRecording() {
        val audioPath = mRecorder?.stopRecording()
        sendAudio(audioPath)
    }

    override fun onCleared() {
        super.onCleared()
        mRecorder = null
    }
}