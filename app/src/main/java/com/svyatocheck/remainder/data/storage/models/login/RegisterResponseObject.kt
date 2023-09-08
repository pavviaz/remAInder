package com.svyatocheck.remainder.data.storage.models.login

import com.google.gson.annotations.SerializedName

data class RegisterResponseObject(
    @SerializedName("id") val id : String,
    @SerializedName("email") val email : String,
    @SerializedName("is_active") val active : Boolean,
    @SerializedName("is_superuser") val sudo : Boolean,
    @SerializedName("is_verified") val verified : Boolean,
    @SerializedName("name") val name : String
)