package com.dyippay.data.repositories

import com.dyippay.data.daos.MovieDao
import com.dyippay.data.entities.FeatureMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieRepository {

    override fun observeMovie(trackId: Long): Flow<FeatureMovie> = movieDao.getMovie(trackId)

    override fun observeRelatedMovies(
        collectionArtistId: Long
    ): Flow<List<FeatureMovie>> = movieDao.getRelatedMovies(collectionArtistId)

    override fun observeUserMustLikeMovies(
        trackId: Long,
        collectionArtistId: Long
    ): Flow<List<FeatureMovie>> = movieDao.getUserMustLikeMovies(trackId, collectionArtistId)
}
