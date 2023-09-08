package com.svyatocheck.remainder.domain.repository

interface IAuthorizationRepository {
    suspend fun launchAuthorization(email : String, password : String) : Boolean

    suspend fun launchRegistration(email : String, name : String, password : String) : Boolean
    suspend fun getUserId(token: String): Boolean
}