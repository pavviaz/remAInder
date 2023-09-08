package com.svyatocheck.remainder.data.storage.remote.recorder

import com.svyatocheck.remainder.data.storage.models.login.NetworkResponse


interface IRecorderRemote {
    suspend fun sendRecord(id: String, audioPath: String?): NetworkResponse?
}
