package com.dyippay.domain.observers

import com.dyippay.data.repositories.TvShowRepository
import com.dyippay.data.resultentities.TvShowWithEpisodes
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTvShowWithEpisodes @Inject constructor(
    private val repository: TvShowRepository
) : SubjectInteractor<ObserveTvShowWithEpisodes.Params, TvShowWithEpisodes>() {

    data class Params(val collectionId: Long)

    override fun createObservable(params: Params): Flow<TvShowWithEpisodes> {
        return repository.observeTvShowAndEpisodes(params.collectionId)
    }
}
