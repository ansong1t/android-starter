package com.dyippay.data.repositories

import com.dyippay.data.daos.AudioBookDao
import com.dyippay.data.entities.AudioBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AudioBookRepositoryImpl @Inject constructor(
    private val audioBookDao: AudioBookDao
) : AudioBookRepository {

    override fun observeAudioBook(collectionId: Long): Flow<AudioBook> =
        audioBookDao.getAudioBook(collectionId)
}
