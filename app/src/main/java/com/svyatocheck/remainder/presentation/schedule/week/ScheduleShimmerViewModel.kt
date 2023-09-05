package com.svyatocheck.remainder.presentation.schedule.week

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScheduleShimmerViewModel : ViewModel() {

    private val _flag = MutableLiveData<Boolean>()
    val flag : LiveData<Boolean> = _flag

    // function to send message
    fun sendMessage(incomingFlag: Boolean) {
        _flag.value = incomingFlag
    }
}