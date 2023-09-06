package com.svyatocheck.remainder.domain.usecases

import com.svyatocheck.remainder.domain.repository.IRecorderRepository

class SendAudioToRemoteUseCase : IRecorderRepository {
    override suspend fun sendAudio(audio: ByteArray): Boolean {
        return true
    }
}