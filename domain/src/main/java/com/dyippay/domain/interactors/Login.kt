package com.dyippay.domain.interactors

import com.dyippay.data.entities.ErrorResult
import com.dyippay.data.repositories.AuthRepository
import com.dyippay.domain.Interactor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Login @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val authRepository: AuthRepository
) : Interactor<Login.Params>() {

    data class Params(val username: String, val password: String)

    override suspend fun doWork(params: Params) {
        return withContext(appCoroutineDispatchers.io) {
            val result = authRepository.login(params.username, params.password)

            if (result is ErrorResult) throw result.throwable
        }
    }
}
