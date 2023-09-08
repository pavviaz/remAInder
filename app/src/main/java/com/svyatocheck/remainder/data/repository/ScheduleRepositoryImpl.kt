package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.core.SharedPrefSettings
import com.svyatocheck.remainder.data.mappers.RemoteToLocalTasks
import com.svyatocheck.remainder.data.storage.models.schedule.ScheduleDataParamModel
import com.svyatocheck.remainder.data.storage.remote.schedule.IScheduleRemote
import com.svyatocheck.remainder.domain.repository.IScheduleRepository
import com.svyatocheck.remainder.presentation.models.ScheduleItem
import java.text.SimpleDateFormat
import java.util.Date


class ScheduleRepositoryImpl(
    private val remoteStorage: IScheduleRemote,
    private val sharedPrefSettings: SharedPrefSettings,
    private val remoteToLocalTasks: RemoteToLocalTasks
) : IScheduleRepository {

    override suspend fun getGroupSchedule(date: Date): List<ScheduleItem> {
        val formatted = SimpleDateFormat("yyyy-MM-dd").format(date)
        return try {
            val repository = remoteStorage.getRemoteSchedule(
                paramModel = ScheduleDataParamModel(sharedPrefSettings.getID(), formatted)
            )
            return remoteToLocalTasks.convertTasks(date, repository)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            emptyList()
        }
    }
}