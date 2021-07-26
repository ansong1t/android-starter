package com.dyippay.data.repositories

import com.dyippay.data.daos.SongDao
import com.dyippay.data.entities.Song
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val songDao: SongDao
) : SongRepository {

    override fun observeSong(trackId: Long): Flow<Song> = songDao.getObservableSong(trackId)

    override fun observeRelatedSongs(trackId: Long, artistId: Long): Flow<List<Song>> =
        songDao.getRelatedSongs(trackId, artistId)

    override fun observeSongsByAlbum(collectionId: Long): Flow<List<Song>> =
        songDao.getSongsByAlbum(collectionId)
}
