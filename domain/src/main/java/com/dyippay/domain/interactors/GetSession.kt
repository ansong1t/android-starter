package com.dyippay.domain.interactors

import com.dyippay.data.entities.session.Session
import com.dyippay.data.repositories.AuthRepository
import com.dyippay.domain.ResultInteractor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSession @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val authRepository: AuthRepository
) : ResultInteractor<GetSession.Params, Session>() {

    object Params

    override suspend fun doWork(params: Params): Session {
        return withContext(appCoroutineDispatchers.io) {
            authRepository.getSession()
        }
    }
}
