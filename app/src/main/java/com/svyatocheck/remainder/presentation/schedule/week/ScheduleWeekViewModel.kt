package com.svyatocheck.remainder.presentation.schedule.week

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import com.svyatocheck.remainder.domain.usecases.GetRemoteTasksUseCase
import com.svyatocheck.remainder.presentation.models.CalendarWeekDay
import com.svyatocheck.remainder.presentation.models.ScheduleItem
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class ScheduleWeekViewModel(
    private val loadRemoteTasks: GetRemoteTasksUseCase
) : ViewModel() {

    // current date
    private val calendar = Calendar.getInstance()

    // range of dates
    private val _calendarWeekDay = MutableLiveData<ArrayList<CalendarWeekDay>>()
    val calendarWeekDay: LiveData<ArrayList<CalendarWeekDay>> = _calendarWeekDay

    // selected day
    private val _selectedPosition = MutableLiveData<Int>()
    var selectedPosition: LiveData<Int> = _selectedPosition

    // schedule list result
    private val _scheduleList = MutableLiveData<List<ScheduleItem>>()
    val scheduleList: LiveData<List<ScheduleItem>> = _scheduleList

    // status
    private val _scheduleLoadingStatus = MutableLiveData<RequestStateStatus>()
    var scheduleLoadingStatus: LiveData<RequestStateStatus> = _scheduleLoadingStatus

    init {
        initCalendarRange()
    }

    private fun initCalendarRange() {
        val calendarList = ArrayList<CalendarWeekDay>()

        // previous month
        val tempCalendar = calendar.clone() as Calendar
        tempCalendar.add(Calendar.MONTH, -1)

        // current month + previous month
        val monthLength = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) +
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // get range of dates from prev.month to next month
        while (calendarList.size < monthLength) {
            if (tempCalendar.time == calendar.time) {
                // if it's a current date, select it
                calendarList.add(CalendarWeekDay(tempCalendar.time, isSelected = true))
                _selectedPosition.value = calendarList.size - 1
            } else {
                calendarList.add(CalendarWeekDay(tempCalendar.time))
            }
            // next date
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        // send new range of dates to the main fragment
        _calendarWeekDay.value = calendarList
    }

    fun loadSchedule(position: Int) {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
            // show message about error
            _scheduleLoadingStatus.value = RequestStateStatus.ERROR
        }

        viewModelScope.launch(handler) {
            val selectedDate = _calendarWeekDay.value!![position]
            _scheduleLoadingStatus.value = RequestStateStatus.LOADING

            _scheduleList.value = withContext(Dispatchers.IO) {
                delay(500)
                Log.d("LOADING", "LOADING, ${_selectedPosition.value}")
                loadRemoteTasks.getRemoteTasks(selectedDate.date)
            } ?: emptyList()

            _scheduleLoadingStatus.value = RequestStateStatus.DONE
        }
    }

    fun resetAction() {
        initCalendarRange()
    }

    fun setSelectedPosition(position: Int) {
        _selectedPosition.value = position
    }
}