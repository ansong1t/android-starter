package com.dyippay.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.dyippay.data.entities.AudioBook
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AudioBookDao : EntityDao<AudioBook>() {

    @Query("SELECT * FROM audio_books WHERE collection_id = :collectionId")
    abstract fun getAudioBook(collectionId: Long): Flow<AudioBook>
}
