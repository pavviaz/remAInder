package com.svyatocheck.remainder.data.storage.models

import com.google.gson.annotations.SerializedName

class GetUserIdQuery(
    @SerializedName("user_id") val id : String
)