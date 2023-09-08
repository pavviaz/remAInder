package com.svyatocheck.remainder.data.storage.remote.recorder

import com.svyatocheck.remainder.data.storage.models.login.NetworkResponse
import com.svyatocheck.remainder.data.storage.remote.RetrofitApiProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class RecorderRemoteImpl : IRecorderRemote {

    private val retrofit: RecorderRemoteApiService = RetrofitApiProvider.createService(
        RecorderRemoteApiService::class.java
    )

    override suspend fun sendRecord(id: String, audioPath: String?): NetworkResponse? {
        // Map is used to multipart the file using okhttp3.RequestBody
        val file: File = audioPath?.let { File(it) } ?: return null

        // Parsing any Media type file
        val requestBody = RequestBody.create("*/*".toMediaTypeOrNull(), file)
        val fileToUpload: MultipartBody.Part =
            MultipartBody.Part.createFormData("file", file.name, requestBody)

        return retrofit.sendAudio(id, fileToUpload)
    }

}