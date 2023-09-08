package com.svyatocheck.remainder.data.storage.models.login

import com.google.gson.annotations.SerializedName

data class NetworkResponse(
    @SerializedName("status") val response: Boolean
)

data class IdResponse(
    @SerializedName("token") val token : String
)