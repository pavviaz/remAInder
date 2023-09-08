package com.svyatocheck.remainder.data.storage.remote.schedule

import com.svyatocheck.remainder.data.storage.models.schedule.SerializedTask
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleRemoteApiService {

    @POST("/api_service/task/get_tasks")
    suspend fun getTasks(
        @Query("user_id") userId: String,
        @Query("date") date: String
    ): List<SerializedTask?>
}