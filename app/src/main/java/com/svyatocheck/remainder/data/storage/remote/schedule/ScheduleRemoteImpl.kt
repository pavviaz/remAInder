package com.svyatocheck.remainder.data.storage.remote.schedule

import com.svyatocheck.remainder.core.SharedPrefSettings
import com.svyatocheck.remainder.data.storage.models.schedule.ScheduleDataParamModel
import com.svyatocheck.remainder.data.storage.models.schedule.SerializedTask
import com.svyatocheck.remainder.data.storage.remote.RetrofitApiProvider

class ScheduleRemoteImpl(
    sharedPrefSettings: SharedPrefSettings
) : IScheduleRemote {

    private val retrofit: ScheduleRemoteApiService = RetrofitApiProvider(sharedPrefSettings).createService(
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