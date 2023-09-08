package com.svyatocheck.remainder.data.storage.models.login

import com.google.gson.annotations.SerializedName

data class RegUser(
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("is_active") val active : Boolean = true,
    @SerializedName("is_superuser") val sudo : Boolean = false,
    @SerializedName("is_verified") val verified : Boolean = false,
    @SerializedName("name") val name : String,
)