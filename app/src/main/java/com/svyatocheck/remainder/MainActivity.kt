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

        if (GlobalContext.getKoinApplicationOrNull() == null) {
            GlobalContext.startKoin {
                androidLogger(Level.DEBUG)
                androidContext(this@MainActivity.applicationContext)
                // schedule fragment from bottom bar
                modules(
                    listOf(
                        scheduleWeekPresentationModule,
                        scheduleWeekDomainModule,
                        scheduleWeekDataModule
                    )
                )

                modules(
                    listOf(
                        recorderPresentationModule,
                        recorderDomainModule,
                        recorderDataModule
                    )
                )

                modules(
                    listOf(
                        authorizationPresentationModule,
                        authorizationDomainModule,
                        authorizationDataModule
                    )
                )

                modules(
                    listOf(
                        registrationPresentationModule,
                        registrationDomainModule,
                        registrationDataModule
                    )
                )

                modules(
                    listOf(
                        appModule
                    )
                )
            }
        }
    }
}