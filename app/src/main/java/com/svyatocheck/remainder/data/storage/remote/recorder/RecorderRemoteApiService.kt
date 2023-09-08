package com.svyatocheck.remainder.data.storage.remote.recorder

import com.svyatocheck.remainder.data.storage.models.login.NetworkResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface RecorderRemoteApiService {

    @Multipart
    @POST("/api_service/task/upload_audio")
    suspend fun sendAudio(
        @Query("user_id") userId: String,
        @Part file: MultipartBody.Part
    ): NetworkResponse?
}