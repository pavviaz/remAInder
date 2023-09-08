package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.domain.usecases.AuthorizationUseCase
import com.svyatocheck.remainder.domain.usecases.GetRemoteTasksUseCase
import com.svyatocheck.remainder.domain.usecases.RegistrationUseCase
import com.svyatocheck.remainder.domain.usecases.SendAudioToRemoteUseCase
import org.koin.dsl.module

val scheduleWeekDomainModule = module {

    factory {
        GetRemoteTasksUseCase(repository = get())
    }

}

val recorderDomainModule = module {

    factory {
        SendAudioToRemoteUseCase(get())
    }

}

val authorizationDomainModule = module {

    factory {
        AuthorizationUseCase(repository = get())
    }

}

val registrationDomainModule = module {

    factory {
        RegistrationUseCase(repository = get())
    }

}