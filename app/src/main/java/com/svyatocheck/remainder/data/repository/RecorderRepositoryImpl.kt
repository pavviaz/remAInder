package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.core.SharedPrefSettings
import com.svyatocheck.remainder.data.storage.remote.recorder.IRecorderRemote
import com.svyatocheck.remainder.domain.repository.IRecorderRepository

class RecorderRepositoryImpl(
    val networking : IRecorderRemote,
    val sharedPrefSettings: SharedPrefSettings
) : IRecorderRepository {

    override suspend fun sendAudio(audioPath: String?): Boolean {
        val userId = sharedPrefSettings.getID()
        val result =  networking.sendRecord(userId, audioPath)
        return result?.response ?: false
    }
}