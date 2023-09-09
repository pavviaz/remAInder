package com.svyatocheck.remainder.data.storage.remote

import com.svyatocheck.remainder.core.SharedPrefSettings
import okhttp3.Interceptor
import okhttp3.OkHttpClient


class BaseInterceptor() {
    companion object {
        private var instance: OkHttpClient? = null
        private var token : String? = null
        private var sharedPrefSettings : SharedPrefSettings? = null

        private fun getInterceptor (token : String): OkHttpClient {
            return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(newRequest)
            }).build()
        }

        fun getInstance(sharedPrefSettings: SharedPrefSettings?): OkHttpClient {
            if (instance == null && this.token.isNullOrBlank()) {
                this.sharedPrefSettings = sharedPrefSettings
                this.token = sharedPrefSettings!!.getToken()
                instance = getInterceptor(this.token!!)
            }
            return instance!!
        }
    }
}