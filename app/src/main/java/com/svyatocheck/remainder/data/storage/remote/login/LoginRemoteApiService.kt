package com.svyatocheck.remainder.data.storage.remote.login

import com.svyatocheck.remainder.data.storage.models.login.AuthResponseObject
import com.svyatocheck.remainder.data.storage.models.login.IdResponse
import com.svyatocheck.remainder.data.storage.models.login.RegUser
import com.svyatocheck.remainder.data.storage.models.login.RegisterResponseObject
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginRemoteApiService {
    @POST("/api_service/auth/jwt/login")
    @FormUrlEncoded
    suspend fun auth(
        @Field("username")  username : String,
        @Field("password")  password : String
    ): AuthResponseObject?

    @POST("/api_service/auth/verify")
    suspend fun getUserId(
        @Body token : IdResponse
    ) : RegisterResponseObject?

    @POST("/api_service/auth/register")
    suspend fun register(
        @Body user: RegUser,
    ): RegisterResponseObject?
}