package com.svyatocheck.remainder.data.storage.models.schedule

import com.google.gson.annotations.SerializedName

data class SerializableScheduleClassroom(
    @SerializedName("lesson_num") val lessonNum: Int?,
    @SerializedName("teacher_name") val teacher: String?,
    @SerializedName("group") val group: String?,
    @SerializedName("subject_name") val subjectName: String?,
    @SerializedName("time") val time: SerializableClassTime?,
)