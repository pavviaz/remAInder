package com.svyatocheck.remainder.presentation.schedule.week

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.svyatocheck.remainder.presentation.models.ScheduleItem

class CalendarViewModel() : ViewModel() {

    private val _dailyTasks: MutableLiveData<List<ScheduleItem>> = MutableLiveData()
    val dailyTasks: LiveData<List<ScheduleItem>> = _dailyTasks

    private val _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int> = _position

    fun sendTasks(list : List<ScheduleItem>){
        _dailyTasks.value = list
    }

    fun setPosition(position : Int){
        _position.value = position
    }
}
