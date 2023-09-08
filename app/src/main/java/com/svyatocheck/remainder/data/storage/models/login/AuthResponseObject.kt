package com.svyatocheck.remainder.data.storage.models.login

import com.google.gson.annotations.SerializedName

data class AuthResponseObject (
    @SerializedName("access_token") val token : String,
    @SerializedName("token_type") val type : String,
)