package com.dyippay.domain.observers

import com.dyippay.data.entities.Song
import com.dyippay.data.repositories.SongRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObserveSong @Inject constructor(
    private val repository: SongRepository
) : SubjectInteractor<ObserveSong.Params, Song>() {

    data class Params(val trackId: Long)

    override fun createObservable(params: Params): Flow<Song> {
        return repository.observeSong(params.trackId)
    }
}
