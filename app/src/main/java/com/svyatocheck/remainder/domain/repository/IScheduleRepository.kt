package com.svyatocheck.remainder.domain.repository

import com.svyatocheck.remainder.domain.models.ScheduleDomainQuery
import com.svyatocheck.remainder.domain.models.ScheduleResultItem

interface IScheduleRepository {
    suspend fun getGroupSchedule(params: ScheduleDomainQuery) : List<ScheduleResultItem>
}
