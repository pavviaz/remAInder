package com.svyatocheck.remainder.data.storage.remote.schedule

import com.svyatocheck.remainder.data.storage.models.schedule.ScheduleDataParamModel
import com.svyatocheck.remainder.data.storage.models.schedule.SerializableScheduleClassroom
import com.svyatocheck.remainder.data.storage.remote.RetrofitApiProvider

class ScheduleRemoteImpl : IScheduleRemote {

    private val retrofit: ScheduleRemoteApiService = RetrofitApiProvider.createService(
        ScheduleRemoteApiService::class.java
    )

    override suspend fun getRemoteSchedule(
        paramModel: ScheduleDataParamModel
    ): List<List<SerializableScheduleClassroom?>?> {
        return retrofit.getClassroomSchedule(paramModel.name, paramModel.offset)
    }
}