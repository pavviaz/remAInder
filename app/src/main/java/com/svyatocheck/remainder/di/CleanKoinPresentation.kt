package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.presentation.login.AuthorizationViewModel
import com.svyatocheck.remainder.presentation.login.RegistrationViewModel
import com.svyatocheck.remainder.presentation.recorder.RecorderViewModel
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleShimmerViewModel
import com.svyatocheck.remainder.presentation.schedule.week.ScheduleWeekViewModel
import com.svyatocheck.remainder.presentation.schedule.week.TasksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Schedule Week
val scheduleWeekPresentationModule = module {
    viewModel {
        ScheduleWeekViewModel(
            loadRemoteTasks = get()
        )
    }
    single { TasksViewModel() }
    single { ScheduleShimmerViewModel() }
}

// Recorder
val recorderPresentationModule = module {
    viewModel {
        RecorderViewModel(
            audioSender = get()
        )
    }
}

// Authorization
val authorizationPresentationModule = module {
    viewModel {
        AuthorizationViewModel(
            authUseCase = get()
        )
    }

}

// Registration
val registrationPresentationModule = module {
    viewModel {
        RegistrationViewModel(
            registerUseCase = get()
        )
    }

}