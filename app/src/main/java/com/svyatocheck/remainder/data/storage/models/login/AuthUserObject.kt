package com.svyatocheck.remainder.data.storage.models.login

import com.google.gson.annotations.SerializedName

data class AuthUserObject(
    @SerializedName("grant_type") val grantType : String? = null,
    @SerializedName("username") val username : String,
    @SerializedName("password") val password: String,
    @SerializedName("scope") val scope: String? = null,
    @SerializedName("client_id") val client : String? = null,
    @SerializedName("client_secret") val clientSecret : String? = null,
)