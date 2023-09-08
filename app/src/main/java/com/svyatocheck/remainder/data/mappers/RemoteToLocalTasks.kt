package com.svyatocheck.remainder.data.mappers

import com.svyatocheck.remainder.data.storage.models.schedule.SerializedTask
import com.svyatocheck.remainder.domain.models.ScheduleItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class RemoteToLocalTasks {

    fun convertTasks(date: Date, tasks: List<SerializedTask?>): List<ScheduleItem> {

        val formatted = SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY).format(date)

        // Filter if there any task from other dates
        val tempList = tasks.filter {
            !it?.datetime.isNullOrBlank() && !it?.description.isNullOrBlank() &&
                    it?.datetime?.contains(formatted) == true
        }

        // For time parsing
        val remoteFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SS", Locale.GERMANY)
        val localFormat = SimpleDateFormat("HH:mm", Locale.GERMANY)

        // Make tasks good for UI
        return tempList.map {
            ScheduleItem(
                localFormat.format(remoteFormat.parse(it!!.datetime!!)!!.time).toString(),
                it.description!!
            )
        }
    }
}