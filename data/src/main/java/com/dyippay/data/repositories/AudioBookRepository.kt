package com.dyippay.data.repositories

import com.dyippay.data.entities.AudioBook
import kotlinx.coroutines.flow.Flow

interface AudioBookRepository {
    fun observeAudioBook(collectionId: Long): Flow<AudioBook>
}
