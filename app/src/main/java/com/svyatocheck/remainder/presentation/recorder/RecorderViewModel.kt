package com.svyatocheck.remainder.presentation.recorder

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RecorderViewModel(
    //private val audioSender : SendAudioToRemoteUseCase
) : ViewModel() {

    private val _networkingStatus = MutableLiveData<RequestStateStatus>()
    var networkingStatus: LiveData<RequestStateStatus> = _networkingStatus

    // creating a variable for media recorder object class.
    private var mRecorder: WavRecorder? = null

    private fun sendAudio(data: ByteArray?) {
        val handler = CoroutineExceptionHandler { _, exception ->
            _networkingStatus.value = RequestStateStatus.ERROR
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler) {
            val flag = withContext(Dispatchers.IO) {
                if (data == null)
                    throw Exception("Empty wav file.")
                //audioSender.sendAudio(byteArrayOutputStream.toByteArray())
                Log.d("DATA", "$data")
                _networkingStatus.postValue(RequestStateStatus.LOADING)
                delay(500)
                true
            } ?: false

            _networkingStatus.value = RequestStateStatus.DONE
        }
    }

    @SuppressLint("MissingPermission")
    fun startRecording(filepath: String) {
        mRecorder = WavRecorder(filepath)
        mRecorder!!.startRecording()
    }

    fun stopRecording() {
        val data = mRecorder?.stopRecording()
        sendAudio(data)
    }

    override fun onCleared() {
        super.onCleared()
        mRecorder = null
    }
}