package com.svyatocheck.remainder.data.storage.remote.login

import com.svyatocheck.remainder.data.storage.models.GetUserIdQuery
import com.svyatocheck.remainder.data.storage.models.login.AuthResponseObject
import com.svyatocheck.remainder.data.storage.models.login.RegUserObject
import com.svyatocheck.remainder.data.storage.models.login.RegisterResponseObject
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRemoteApiService {

    @POST("/api_service/auth/jwt/login")
    @FormUrlEncoded
    suspend fun auth(
        @Field("username")  username : String,
        @Field("password")  password : String
    ): AuthResponseObject?


    @GET("/api_service/auth/verify")
    suspend fun getUserId(
        @Header("Authorization") token : String
    ) : GetUserIdQuery?


    @POST("/api_service/auth/register")
    suspend fun register(
        @Body user: RegUserObject,
    ): RegisterResponseObject?
}