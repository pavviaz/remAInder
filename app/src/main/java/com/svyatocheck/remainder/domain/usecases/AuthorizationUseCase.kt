package com.svyatocheck.remainder.domain.usecases

import com.svyatocheck.remainder.domain.repository.IAuthorizationRepository

class AuthorizationUseCase(private val repository: IAuthorizationRepository) {
    suspend fun launchAuthorization(email: String, password: String): Boolean {
        return repository.launchAuthorization(email, password)
    }

}
