package com.svyatocheck.remainder.data.storage.remote.login

import com.svyatocheck.remainder.data.storage.models.login.AuthResponseObject
import com.svyatocheck.remainder.data.storage.models.login.AuthUser
import com.svyatocheck.remainder.data.storage.models.login.RegUser
import com.svyatocheck.remainder.data.storage.models.login.RegisterResponseObject


interface ILoginRemote {
    suspend fun authUser(user : AuthUser) : AuthResponseObject?

    suspend fun registerUser(user : RegUser) : RegisterResponseObject?

    suspend fun getUserId(token : String) : RegisterResponseObject?
}
