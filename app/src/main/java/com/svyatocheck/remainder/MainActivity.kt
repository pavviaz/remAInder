package com.svyatocheck.remainder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            }
        }
    }
}