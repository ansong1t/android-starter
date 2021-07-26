package com.dyippay.domain.observers

import com.dyippay.data.entities.Song
import com.dyippay.data.repositories.SongRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ObserveAlbumSongs @Inject constructor(
    private val repository: SongRepository
) : SubjectInteractor<ObserveAlbumSongs.Params, List<Song>>() {

    data class Params(val collectionId: Long)

    override fun createObservable(params: Params): Flow<List<Song>> {
        return repository.observeSongsByAlbum(params.collectionId)
    }
}
