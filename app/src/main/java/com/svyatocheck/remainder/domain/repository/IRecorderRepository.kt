package com.svyatocheck.remainder.domain.repository

interface IRecorderRepository {
    suspend fun sendAudio(audioPath: String?): Boolean
}