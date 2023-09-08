package com.svyatocheck.remainder.data.storage.remote.login

import com.svyatocheck.remainder.data.storage.models.login.AuthResponseObject
import com.svyatocheck.remainder.data.storage.models.login.AuthUserObject
import com.svyatocheck.remainder.data.storage.models.login.IdResponse
import com.svyatocheck.remainder.data.storage.models.login.RegUserObject
import com.svyatocheck.remainder.data.storage.models.login.RegisterResponseObject
import com.svyatocheck.remainder.data.storage.remote.RetrofitApiProvider

class LoginRemoteImpl : ILoginRemote {

    private val retrofit: LoginRemoteApiService = RetrofitApiProvider.createService(
        LoginRemoteApiService::class.java
    )

    override suspend fun authUser(user : AuthUserObject): AuthResponseObject? {
        return retrofit.auth(user.username, user.password)
    }

    override suspend fun registerUser(user: RegUserObject): RegisterResponseObject? {
        return retrofit.register(user)
    }

    override suspend fun getUserId(token: String): RegisterResponseObject? {
        return retrofit.getUserId(IdResponse( token))
    }

}