package com.svyatocheck.remainder.data.storage.models.schedule

import com.google.gson.annotations.SerializedName

data class SerializedTask(
    @SerializedName("id") val taskId: String?,
    @SerializedName("datetime") val datetime: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("user_id") val userId: String?,
    @SerializedName("duration") val duration: String?,
    @SerializedName("embedding") val embedding: String?,
)