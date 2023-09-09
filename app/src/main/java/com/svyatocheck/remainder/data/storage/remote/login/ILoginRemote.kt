package com.svyatocheck.remainder.data.storage.remote.login

import com.svyatocheck.remainder.data.storage.models.GetUserIdQuery
import com.svyatocheck.remainder.data.storage.models.login.AuthResponseObject
import com.svyatocheck.remainder.data.storage.models.login.AuthUserObject
import com.svyatocheck.remainder.data.storage.models.login.RegUserObject
import com.svyatocheck.remainder.data.storage.models.login.RegisterResponseObject


interface ILoginRemote {
    suspend fun authUser(user : AuthUserObject) : AuthResponseObject?

    suspend fun registerUser(user : RegUserObject) : RegisterResponseObject?

    suspend fun getUserId(token : String) : GetUserIdQuery?
}
