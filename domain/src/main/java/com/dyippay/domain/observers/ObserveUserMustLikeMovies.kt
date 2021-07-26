package com.dyippay.domain.observers

import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.repositories.MovieRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObserveUserMustLikeMovies @Inject constructor(
    private val repository: MovieRepository
) : SubjectInteractor<ObserveUserMustLikeMovies.Params, List<FeatureMovie>>() {

    data class Params(val currentTrackId: Long, val collectionArtistId: Long)

    override fun createObservable(params: Params): Flow<List<FeatureMovie>> {
        return repository.observeUserMustLikeMovies(
            params.currentTrackId,
            params.collectionArtistId
        )
    }
}
