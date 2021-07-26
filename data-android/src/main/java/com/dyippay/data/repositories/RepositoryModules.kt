package com.dyippay.data.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModules {

    @Binds
    @Singleton
    abstract fun bindItemRepository(repository: ItemRepositoryImpl): ItemRepository

    @Binds
    @Singleton
    abstract fun bindTvShowRepository(repository: TvShowRepositoryImpl): TvShowRepository

    @Binds
    @Singleton
    abstract fun bindSongRepository(repository: SongRepositoryImpl): SongRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Singleton
    abstract fun bindAudioBookRepository(repository: AudioBookRepositoryImpl): AudioBookRepository
}
