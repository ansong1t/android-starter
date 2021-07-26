package com.dyippay.domain.observers

import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.repositories.MovieRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObserveRelatedMovies @Inject constructor(
    private val repository: MovieRepository
) : SubjectInteractor<ObserveRelatedMovies.Params, List<FeatureMovie>>() {

    data class Params(val collectionArtistId: Long)

    override fun createObservable(params: Params): Flow<List<FeatureMovie>> {
        return repository.observeRelatedMovies(params.collectionArtistId)
    }
}
