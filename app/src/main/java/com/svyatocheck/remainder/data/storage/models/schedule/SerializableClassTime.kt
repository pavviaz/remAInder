package com.svyatocheck.remainder.data.storage.models.schedule

import com.google.gson.annotations.SerializedName

data class SerializableClassTime(
    @SerializedName("hours") val hours: Int,
    @SerializedName("minutes") val minutes: Int
)