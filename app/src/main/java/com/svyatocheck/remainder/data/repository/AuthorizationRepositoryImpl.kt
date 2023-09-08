package com.svyatocheck.remainder.data.repository

import com.svyatocheck.remainder.core.SharedPrefSettings
import com.svyatocheck.remainder.data.storage.models.login.AuthUserObject
import com.svyatocheck.remainder.data.storage.models.login.RegUserObject
import com.svyatocheck.remainder.data.storage.remote.login.ILoginRemote
import com.svyatocheck.remainder.domain.repository.IAuthorizationRepository

class AuthorizationRepositoryImpl(
    private val networking: ILoginRemote,
    private val sharedPrefSettings: SharedPrefSettings
) : IAuthorizationRepository {

    override suspend fun launchAuthorization(email: String, password: String): Boolean {
        val response = networking.authUser(
            AuthUserObject(
                username = email,
                password = password
            )
        )
        // save user's token
        response?.let { sharedPrefSettings.putToken(it.token) } ?: return false
        // save user's id (temporary)
        if (!getUserId(response.token)) return false
        return true
    }

    override suspend fun launchRegistration(
        email: String,
        name: String,
        password: String
    ): Boolean {
        val response = networking.registerUser(
            RegUserObject(
                email = email,
                password = password,
                name = name
            )
        ) ?: return false

        // save user's id
        sharedPrefSettings.putID(response.id)
        // auth user
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