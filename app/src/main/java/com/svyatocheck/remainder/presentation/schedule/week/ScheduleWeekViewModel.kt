package com.svyatocheck.remainder.presentation.schedule.week

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.application.feature_schedule.presentation.utills.RequestStateStatus
import com.svyatocheck.remainder.presentation.models.CalendarWeekDay
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.system.measureTimeMillis

class ScheduleWeekViewModel(
//    private val loadRemoteTasks: GetRemoteTasks
) : ViewModel() {

    // current date
    private val calendar = Calendar.getInstance()

    // range of dates
    private val _calendarWeekDay = MutableLiveData<ArrayList<CalendarWeekDay>>()
    val calendarWeekDay: LiveData<ArrayList<CalendarWeekDay>> = _calendarWeekDay

    // selected day
    private val _selectedPosition = MutableLiveData<Int>()
    var selectedPosition: LiveData<Int> = _selectedPosition

//    // schedule list result
//    private val _scheduleList = MutableLiveData<List<ScheduleDailyItem>>()
//    val scheduleList: LiveData<List<ScheduleDailyItem>> = _scheduleList

    // status
    private val _scheduleLoadingStatus = MutableLiveData<RequestStateStatus>()
    var scheduleLoadingStatus: LiveData<RequestStateStatus> = _scheduleLoadingStatus

    private val _scheduleLocalLoadingStatus = MutableLiveData<RequestStateStatus>()
    var scheduleLocalLoadingStatus: LiveData<RequestStateStatus> = _scheduleLocalLoadingStatus

    private val _innerOperationsStatus = MutableLiveData<RequestStateStatus>()
    var innerOperationStatus: LiveData<RequestStateStatus> = _innerOperationsStatus

    init {
        initCalendarRange()
    }

    private fun initCalendarRange() {
        val calendarList = ArrayList<CalendarWeekDay>()

        // previous month
        val toolCalendar = calendar.clone() as Calendar
        toolCalendar.add(Calendar.MONTH, -1)

        // current month + previous month
        val monthLength = toolCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) +
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        // get range of dates from prev.month to next month
        while (calendarList.size < monthLength) {
            if (toolCalendar.time == calendar.time) {
                // if it's a current date, select it
                calendarList.add(CalendarWeekDay(toolCalendar.time, isSelected = true))
                _selectedPosition.value = calendarList.size - 1
            } else {
                calendarList.add(CalendarWeekDay(toolCalendar.time))
            }
            // next date
            toolCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        // send new range of dates to the main fragment
        _calendarWeekDay.value = calendarList
    }

    fun loadSchedule() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
            // show message about error
            _scheduleLoadingStatus.value = RequestStateStatus.ERROR
        }

        viewModelScope.launch(handler) {
            _scheduleLoadingStatus.value = RequestStateStatus.LOADING
            val time = measureTimeMillis {
                delay(1500)
            }

            if (time < 1000)
                delay(1000 - time)

//            _scheduleList.value = result.await()
            _scheduleLoadingStatus.value = RequestStateStatus.DONE
        }
    }

    private suspend fun loadRemoteTasks() {
//        scheduleParamModel: ScheduleParametersDomainModel
//    ): List<ScheduleDailyItem> {
//        return withContext(Dispatchers.IO) {
//            return@withContext loadRemoteTasks.execute(
//                scheduleParamModel
//            )
//        }
    }

    fun resetAction() {
        initCalendarRange()
    }

    fun setSelectedPosition(position: Int) {
        _selectedPosition.value = position
    }
}