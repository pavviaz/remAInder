package com.svyatocheck.remainder.di

import com.svyatocheck.remainder.core.SharedPrefSettings
import com.svyatocheck.remainder.data.mappers.RemoteToLocalTasks
import com.svyatocheck.remainder.data.repository.AuthorizationRepositoryImpl
import com.svyatocheck.remainder.data.repository.RecorderRepositoryImpl
import com.svyatocheck.remainder.data.repository.ScheduleRepositoryImpl
import com.svyatocheck.remainder.data.storage.remote.login.ILoginRemote
import com.svyatocheck.remainder.data.storage.remote.login.LoginRemoteImpl
import com.svyatocheck.remainder.data.storage.remote.recorder.IRecorderRemote
import com.svyatocheck.remainder.data.storage.remote.recorder.RecorderRemoteImpl
import com.svyatocheck.remainder.data.storage.remote.schedule.IScheduleRemote
import com.svyatocheck.remainder.data.storage.remote.schedule.ScheduleRemoteImpl
import com.svyatocheck.remainder.domain.repository.IAuthorizationRepository
import com.svyatocheck.remainder.domain.repository.IRecorderRepository
import com.svyatocheck.remainder.domain.repository.IScheduleRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

// Schedule
val scheduleWeekDataModule = module {
    single<IScheduleRemote> {
        ScheduleRemoteImpl()
    }
    single {
        RemoteToLocalTasks()
    }
    single<IScheduleRepository> {
        ScheduleRepositoryImpl(
            remoteStorage = get(), sharedPrefSettings = get(), remoteToLocalTasks = get()
        )
    }
}

// Recorder
val recorderDataModule = module {
    single<IRecorderRemote> {
        RecorderRemoteImpl()
    }
    single<IRecorderRepository> {
        RecorderRepositoryImpl(
            networking = get(),
            sharedPrefSettings = get()
        )
    }
}

// Authorization
val authorizationDataModule = module {
    single<ILoginRemote> {
        LoginRemoteImpl()
    }
    single<IAuthorizationRepository> {
        AuthorizationRepositoryImpl(networking = get(), sharedPrefSettings = get())
    }
}

// Registration
val registrationDataModule = module {
    single<ILoginRemote> {
        LoginRemoteImpl()
    }
}

// Global
val appModule = module {
    single<SharedPrefSettings> {
        SharedPrefSettings(androidApplication().applicationContext)
    }
}