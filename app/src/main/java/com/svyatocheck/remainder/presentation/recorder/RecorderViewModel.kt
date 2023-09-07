package com.svyatocheck.remainder.presentation.recorder

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
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
import java.io.ByteArrayOutputStream


class RecorderViewModel(
    //private val audioSender : SendAudioToRemoteUseCase
) : ViewModel() {

    private val _networkingStatus = MutableLiveData<RequestStateStatus>()
    var networkingStatus: LiveData<RequestStateStatus> = _networkingStatus

    // creating a variable for media recorder object class.
    private var mRecorder: AudioRecord? = null
    private var isRecording: Boolean = false
    private var myBufferSize = 10240

    lateinit var filepath: String

    // byte array for audio record
    private val byteArrayOutputStream by lazy { ByteArrayOutputStream() }

    private fun makeByteArray(filepath: String) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler) {
            isRecording = true
            withContext(Dispatchers.IO) {
                if (mRecorder == null) return@withContext

                val myBuffer = ByteArray(myBufferSize)
                var readCount: Int
                var totalCount = 0
                while (isRecording) {
                    readCount = mRecorder!!.read(myBuffer, 0, myBufferSize)
                    totalCount += readCount
                    Log.d(
                        "READING", "readCount = " + readCount + ", totalCount = "
                                + totalCount
                    )
                }
            }
        }
    }

    private fun sendAudio() {
        val handler = CoroutineExceptionHandler { _, exception ->
            _networkingStatus.value = RequestStateStatus.ERROR
            println("CoroutineExceptionHandler got $exception")
        }

        viewModelScope.launch(handler) {
            val flag = withContext(Dispatchers.IO) {
                //audioSender.sendAudio(byteArrayOutputStream.toByteArray())
                _networkingStatus.postValue(RequestStateStatus.LOADING)
                delay(500)
                true
            } ?: false

            _networkingStatus.value = RequestStateStatus.DONE
        }
    }

    @SuppressLint("MissingPermission")
    fun startRecording(filepath: String) {
        val sampleRate = 16000
        val channelConfig = AudioFormat.CHANNEL_IN_MONO
        val audioFormat = AudioFormat.ENCODING_PCM_16BIT

        val minInternalBufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            channelConfig, audioFormat
        )
        val internalBufferSize = minInternalBufferSize * 4
        Log.d(
            "AUDIO INIT", "minInternalBufferSize = " + minInternalBufferSize
                    + ", internalBufferSize = " + internalBufferSize
                    + ", myBufferSize = " + myBufferSize
        )

        mRecorder = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate, channelConfig, audioFormat, internalBufferSize
        )

        mRecorder!!.startRecording()
        makeByteArray(filepath)
    }

    fun stopRecording() {
        isRecording = false
        mRecorder?.apply {
            stop()
            release()
        }
        mRecorder = null

        sendAudio()
        byteArrayOutputStream.flush()
    }

    override fun onCleared() {
        super.onCleared()
        isRecording = false
        mRecorder?.release()
        mRecorder = null
    }
}