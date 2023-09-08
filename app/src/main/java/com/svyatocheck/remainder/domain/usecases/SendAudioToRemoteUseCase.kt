package com.svyatocheck.remainder.domain.usecases

import com.svyatocheck.remainder.domain.repository.IRecorderRepository

class SendAudioToRemoteUseCase(private val repository: IRecorderRepository){
    suspend fun sendAudio(audioPath: String?): Boolean {
        return repository.sendAudio(audioPath)
    }
}