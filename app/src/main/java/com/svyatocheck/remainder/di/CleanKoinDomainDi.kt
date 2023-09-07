package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.domain.usecases.GetRemoteTasksUseCase
import org.koin.dsl.module

val scheduleWeekDomainModule = module {

    factory {
        GetRemoteTasksUseCase(repository = get())
    }

}

val recorderDomainModule = module {

//    factory {
//        GetRemoteTasks(repository = get())
//    }

}