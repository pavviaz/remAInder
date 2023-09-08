package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.presentation.login.AuthorizationViewModel
import com.svyatocheck.remainder.presentation.login.RegistrationViewModel
import com.svyatocheck.remainder.presentation.recorder.RecorderViewModel
import com.svyatocheck.remainder.presentation.schedule.week.CalendarViewModel
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleShimmerViewModel
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleWeekViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val scheduleWeekPresentationModule = module {
    // Week Schedule
    viewModel {
        ScheduleWeekViewModel(
            loadRemoteTasks = get()
        )
    }

    single { CalendarViewModel() }

    single { ScheduleShimmerViewModel() }

}

val recorderPresentationModule = module {

    viewModel {
        RecorderViewModel(
            audioSender = get()
        )
    }
}

val authorizationPresentationModule = module {
    // Week Schedule
    viewModel {
        AuthorizationViewModel(
            authUseCase = get()
        )
    }

}

val registrationPresentationModule = module {
    // Week Schedule
    viewModel {
        RegistrationViewModel(
            registerUseCase = get()
        )
    }

}