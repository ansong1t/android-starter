package com.dyippay.domain.interactors

import com.dyippay.data.entities.session.Session
import com.dyippay.data.util.Stubs
import com.dyippay.domain.ResultInteractor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSession @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ResultInteractor<GetSession.Params, Session>() {

    object Params

    override suspend fun doWork(params: Params): Session {
        return withContext(appCoroutineDispatchers.io) {
            delay(1000)
            Stubs.SESSION_NOT_LOGGED_IN
        }
    }
}
