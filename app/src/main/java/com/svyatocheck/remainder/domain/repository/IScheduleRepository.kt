package com.svyatocheck.remainder.domain.repository

import com.svyatocheck.remainder.domain.models.ScheduleItem
import java.util.Date

interface IScheduleRepository {
    suspend fun getGroupSchedule(date: Date) : List<ScheduleItem>
}
