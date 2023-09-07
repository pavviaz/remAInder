package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.data.repository.ScheduleRepositoryImpl
import com.svyatocheck.remainder.domain.repository.IScheduleRepository
import org.koin.dsl.module

val scheduleWeekDataModule = module {

//    single<IScheduleRemote> {
//        ScheduleRemoteImpl()
//    }
//
    single<IScheduleRepository> {
        ScheduleRepositoryImpl(
            //remoteStorage = get()
        )
    }
}

val recorderDataModule = module {

//    single<IScheduleRemote> {
//        ScheduleRemoteImpl()
//    }
//
//    single<IScheduleRepository> {
//        ScheduleRepositoryImpl(remoteStorage = get())
//    }
}