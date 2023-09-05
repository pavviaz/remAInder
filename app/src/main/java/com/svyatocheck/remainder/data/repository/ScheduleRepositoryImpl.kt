package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.data.storage.remote.schedule.IScheduleRemote
import com.svyatocheck.remainder.domain.models.ScheduleDomainQuery
import com.svyatocheck.remainder.domain.models.ScheduleResultItem
import com.svyatocheck.remainder.domain.repository.IScheduleRepository


class ScheduleRepositoryImpl(
    private val remoteStorage: IScheduleRemote
) : IScheduleRepository {

    override suspend fun getGroupSchedule(params: ScheduleDomainQuery): List<ScheduleResultItem> {
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