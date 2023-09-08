package com.svyatocheck.remainder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.svyatocheck.remainder.di.appModule
import com.svyatocheck.remainder.di.authorizationDataModule
import com.svyatocheck.remainder.di.authorizationDomainModule
import com.svyatocheck.remainder.di.authorizationPresentationModule
import com.svyatocheck.remainder.di.recorderDataModule
import com.svyatocheck.remainder.di.recorderDomainModule
import com.svyatocheck.remainder.di.recorderPresentationModule
import com.svyatocheck.remainder.di.registrationDataModule
import com.svyatocheck.remainder.di.registrationDomainModule
import com.svyatocheck.remainder.di.registrationPresentationModule
import com.svyatocheck.remainder.di.scheduleWeekDataModule
import com.svyatocheck.remainder.di.scheduleWeekDomainModule
import com.svyatocheck.remainder.di.scheduleWeekPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Dependency Injection with Koin
        if (GlobalContext.getKoinApplicationOrNull() == null) {
            GlobalContext.startKoin {
                androidLogger(Level.DEBUG)
                androidContext(this@MainActivity.applicationContext)

                // Schedule Tasks Fragment
                modules(
                    listOf(
                        scheduleWeekPresentationModule,
                        scheduleWeekDomainModule,
                        scheduleWeekDataModule
                    )
                )

                // Edit tasks (Recorder) Fragment
                modules(
                    listOf(
                        recorderPresentationModule,
                        recorderDomainModule,
                        recorderDataModule
                    )
                )

                // Authorization Fragment
                modules(
                    listOf(
                        authorizationPresentationModule,
                        authorizationDomainModule,
                        authorizationDataModule
                    )
                )

                // Registration Fragment
                modules(
                    listOf(
                        registrationPresentationModule,
                        registrationDomainModule,
                        registrationDataModule
                    )
                )

                // Global dependencies
                modules(
                    listOf(
                        appModule
                    )
                )
            }
        }
    }
}