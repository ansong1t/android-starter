package com.dyippay.domain.observers

import com.dyippay.data.entities.Song
import com.dyippay.data.repositories.SongRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveRelatedSongs @Inject constructor(
    private val repository: SongRepository
) : SubjectInteractor<ObserveRelatedSongs.Params, List<Song>>() {

    data class Params(val trackId: Long, val artistId: Long)

    override fun createObservable(params: Params): Flow<List<Song>> {
        return repository.observeRelatedSongs(params.trackId, params.artistId)
    }
}
