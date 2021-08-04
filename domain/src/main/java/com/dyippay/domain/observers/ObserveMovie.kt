package com.dyippay.domain.observers

import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.repositories.MovieRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMovie @Inject constructor(
    private val repository: MovieRepository
) : SubjectInteractor<ObserveMovie.Params, FeatureMovie>() {

    data class Params(val trackId: Long)

    override fun createObservable(params: Params): Flow<FeatureMovie> {
        return repository.observeMovie(params.trackId)
    }
}
