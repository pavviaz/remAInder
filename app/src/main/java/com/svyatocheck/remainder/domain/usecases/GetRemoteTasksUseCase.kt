package com.svyatocheck.remainder.domain.usecases

import com.svyatocheck.remainder.domain.repository.IScheduleRepository
import com.svyatocheck.remainder.presentation.models.ScheduleItem
import java.util.Date

class GetRemoteTasksUseCase(private val repository: IScheduleRepository) {

    suspend fun getRemoteTasks(date : Date): List<ScheduleItem> {
        return repository.getGroupSchedule(date)
    }

}
