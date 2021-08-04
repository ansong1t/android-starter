package com.dyippay.domain.observers

import com.dyippay.data.entities.AudioBook
import com.dyippay.data.repositories.AudioBookRepository
import com.dyippay.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAudioBook @Inject constructor(
    private val repository: AudioBookRepository
) : SubjectInteractor<ObserveAudioBook.Params, AudioBook>() {

    data class Params(val collectionId: Long)

    override fun createObservable(params: Params): Flow<AudioBook> {
        return repository.observeAudioBook(params.collectionId)
    }
}
