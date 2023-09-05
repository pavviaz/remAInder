package com.svyatocheck.remainder.data.storage.models.schedule

import com.google.gson.annotations.SerializedName

data class SerializableClassGroup(
    @SerializedName("auditory") val auditory: String,
    @SerializedName("teacher") val teacher: String,
    @SerializedName("subject_name") val subjectName: String,
)