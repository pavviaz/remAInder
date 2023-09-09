package com.svyatocheck.remainder.data.storage.remote

import com.svyatocheck.remainder.core.SharedPrefSettings
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://51.250.81.200:7000"

class RetrofitApiProvider(sharedPrefSettings: SharedPrefSettings) {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(BaseInterceptor.getInstance(sharedPrefSettings))
        .build()

    fun <S> createService(serviceClass: Class<S>?): S {
        return retrofit.create(serviceClass!!)
    }
}



