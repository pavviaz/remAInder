package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.domain.repository.IRecorderRepository

class RecorderRepositoryImpl : IRecorderRepository {
    override suspend fun sendAudio(audio: ByteArray): Boolean {
        return true
    }
}