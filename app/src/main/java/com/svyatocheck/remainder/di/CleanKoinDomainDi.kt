package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.domain.usecases.AuthorizationUseCase
import com.svyatocheck.remainder.domain.usecases.GetRemoteTasksUseCase
import com.svyatocheck.remainder.domain.usecases.RegistrationUseCase
import com.svyatocheck.remainder.domain.usecases.SendAudioToRemoteUseCase
import org.koin.dsl.module

// Schedule
val scheduleWeekDomainModule = module {
    factory {
        GetRemoteTasksUseCase(repository = get())
    }
}

// Recorder
val recorderDomainModule = module {
    factory {
        SendAudioToRemoteUseCase(get())
    }
}

// Authorization
val authorizationDomainModule = module {
    factory {
        AuthorizationUseCase(repository = get())
    }
}

// Registration
val registrationDomainModule = module {
    factory {
        RegistrationUseCase(repository = get())
    }
}