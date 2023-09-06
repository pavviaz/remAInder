package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.presentation.recorder.RecorderViewModel
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleShimmerViewModel
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleWeekViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val scheduleWeekPresentationModule = module {

    // Week Schedule
    viewModel {
        ScheduleWeekViewModel(
//            loadRemoteTasks = get()
        )
    }

    single {
        ScheduleShimmerViewModel()
    }

}

val recorderPresentationModule = module {

    viewModel {
        RecorderViewModel(
//            audioSender = get()
        )
    }

}