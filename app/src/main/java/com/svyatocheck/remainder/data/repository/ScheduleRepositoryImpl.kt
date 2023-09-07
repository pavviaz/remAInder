package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.domain.repository.IScheduleRepository
import com.svyatocheck.remainder.presentation.models.ScheduleItem
import java.util.Date


class ScheduleRepositoryImpl(
    //private val remoteStorage: IScheduleRemote
) : IScheduleRepository {

    override suspend fun getGroupSchedule(params: Date): List<ScheduleItem> {
//        val result = remoteStorage.getRemoteSchedule(
//            paramModel = ScheduleDataParamModel(params.name, "")
//        )
//        return try {
////            classesMapper.mapToDomain(result)
//            emptyList<ScheduleResultItem>()
//        } catch (ex: NullPointerException) {
//            ex.printStackTrace()
//            emptyList()
//        }
        return emptyList()
    }
}