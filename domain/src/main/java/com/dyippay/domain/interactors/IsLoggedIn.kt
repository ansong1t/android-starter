package com.dyippay.domain.interactors

import com.dyippay.data.repositories.AuthRepository
import com.dyippay.domain.ResultInteractor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsLoggedIn @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val authRepository: AuthRepository
) : ResultInteractor<IsLoggedIn.Params, Boolean>() {

    object Params

    override suspend fun doWork(params: Params): Boolean {
        return withContext(appCoroutineDispatchers.io) {
            authRepository.getSession().accessToken.token.isNotEmpty()
        }
    }
}
