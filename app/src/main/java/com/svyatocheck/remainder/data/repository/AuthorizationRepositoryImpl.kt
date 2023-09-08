package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.core.SharedPrefSettings
import com.svyatocheck.remainder.data.storage.models.login.AuthUser
import com.svyatocheck.remainder.data.storage.models.login.RegUser
import com.svyatocheck.remainder.data.storage.remote.login.ILoginRemote
import com.svyatocheck.remainder.domain.repository.IAuthorizationRepository

class AuthorizationRepositoryImpl(
    private val networking: ILoginRemote,
    private val sharedPrefSettings: SharedPrefSettings
) : IAuthorizationRepository {


    override suspend fun launchAuthorization(email: String, password: String): Boolean {
        val response = networking.authUser(
            AuthUser(
                username = email,
                password = password
            )
        )
        response?.let { sharedPrefSettings.putToken(it.token) } ?: return false
        if (!getUserId(response.token)) return false
        return true
    }

    override suspend fun launchRegistration(
        email: String,
        name: String,
        password: String
    ): Boolean {
        val response = networking.registerUser(
            RegUser(
                email = email,
                password = password,
                name = name
            )
        ) ?: return false

        sharedPrefSettings.putID(response.id)
        val auth = launchAuthorization(email, password)
        if (auth)
            return true

        return false
    }

    override suspend fun getUserId(
        token: String
    ): Boolean {
        val response = networking.getUserId(token) ?: return false
        sharedPrefSettings.putID(response.id)
        return true
    }
}