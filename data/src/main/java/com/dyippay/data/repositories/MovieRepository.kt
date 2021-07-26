package com.dyippay.data.repositories

import com.dyippay.data.entities.FeatureMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun observeMovie(trackId: Long): Flow<FeatureMovie>
    fun observeRelatedMovies(collectionArtistId: Long): Flow<List<FeatureMovie>>
    fun observeUserMustLikeMovies(trackId: Long, collectionArtistId: Long): Flow<List<FeatureMovie>>
}
