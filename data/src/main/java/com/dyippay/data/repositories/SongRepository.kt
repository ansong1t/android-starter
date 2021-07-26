package com.dyippay.data.repositories

import com.dyippay.data.entities.Song
import kotlinx.coroutines.flow.Flow

interface SongRepository {
    fun observeSong(trackId: Long): Flow<Song>
    fun observeRelatedSongs(trackId: Long, artistId: Long): Flow<List<Song>>
    fun observeSongsByAlbum(collectionId: Long): Flow<List<Song>>
}
