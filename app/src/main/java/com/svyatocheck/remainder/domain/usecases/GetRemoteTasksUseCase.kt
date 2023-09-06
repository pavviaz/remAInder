package com.svyatocheck.remainder.domain.usecases

import com.svyatocheck.remainder.domain.models.ScheduleDomainQuery
import com.svyatocheck.remainder.domain.models.ScheduleResultItem
import com.svyatocheck.remainder.domain.repository.IScheduleRepository

class GetRemoteTasksUseCase(private val repository: IScheduleRepository) {

    suspend fun getRemoteTasks(queryParams : ScheduleDomainQuery): List<ScheduleResultItem> {
        return repository.getGroupSchedule(queryParams)
    }

}
