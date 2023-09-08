package com.svyatocheck.remainder.domain.usecases

import com.svyatocheck.remainder.domain.repository.IAuthorizationRepository

class RegistrationUseCase(val repository : IAuthorizationRepository) {
    suspend fun launchRegistration(email : String, password : String, name :String): Boolean {
        return repository.launchRegistration(email, name, password)
    }

}