package com.svyatocheck.remainder.data.storage.remote.recorder


interface IRecorderRemote {
    suspend fun sendRecord(audio : ByteArray): Boolean
}
