package com.svyatocheck.remainder.data.storage.remote.recorder

import com.svyatocheck.remainder.data.storage.remote.RetrofitApiProvider

class RecorderRemoteImpl : IRecorderRemote {

    private val retrofit: RecorderRemoteApiService = RetrofitApiProvider.createService(
        RecorderRemoteApiService::class.java
    )

    override suspend fun sendRecord(audio: ByteArray): Boolean {
        return true
    }

}