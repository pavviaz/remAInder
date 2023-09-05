package com.svyatocheck.remainder.data.storage.remote.schedule

import com.svyatocheck.remainder.data.storage.models.schedule.ScheduleDataParamModel
import com.svyatocheck.remainder.data.storage.models.schedule.SerializableScheduleClassroom


interface IScheduleRemote {
    suspend fun getRemoteSchedule(paramModel: ScheduleDataParamModel): List<List<SerializableScheduleClassroom?>?>
}
