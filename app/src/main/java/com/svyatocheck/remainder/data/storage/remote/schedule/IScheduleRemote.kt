package com.svyatocheck.remainder.data.storage.remote.schedule

import com.svyatocheck.remainder.data.storage.models.schedule.ScheduleDataParamModel
import com.svyatocheck.remainder.data.storage.models.schedule.SerializedTask


interface IScheduleRemote {
    suspend fun getRemoteSchedule(paramModel: ScheduleDataParamModel): List<SerializedTask?>
}
