package com.svyatocheck.remainder.data.storage.remote.recorder

import com.svyatocheck.remainder.data.storage.models.schedule.SerializableScheduleClassroom
import retrofit2.http.GET
import retrofit2.http.Query

interface RecorderRemoteApiService {

    @GET("getScheduleClassroom")
    suspend fun getClassroomSchedule(
        @Query("classroom") classroom: String,
        @Query("week_offset") weekOffset: String
    ): List<List<SerializableScheduleClassroom>?>
}