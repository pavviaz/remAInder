package com.svyatocheck.remainder.data.storage.remote.schedule

import com.svyatocheck.remainder.data.storage.models.schedule.ScheduleDataParamModel
import com.svyatocheck.remainder.data.storage.models.schedule.SerializedTask
import com.svyatocheck.remainder.data.storage.remote.RetrofitApiProvider

class ScheduleRemoteImpl : IScheduleRemote {

    private val retrofit: ScheduleRemoteApiService = RetrofitApiProvider.createService(
        ScheduleRemoteApiService::class.java
    )

    override suspend fun getRemoteSchedule(
        paramModel: ScheduleDataParamModel
    ): List<SerializedTask?> {
        return retrofit.getTasks(
            paramModel.userId, paramModel.date
        )
    }
}