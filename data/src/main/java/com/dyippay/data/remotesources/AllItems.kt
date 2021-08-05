package com.dyippay.data.remotesources

import com.dyippay.data.entities.TvEpisode
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.TvShow
import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.entities.AudioBook
import com.dyippay.data.entities.ItemEntry

data class AllItems(
    val tvEpisodes: List<TvEpisode> = emptyList(),
    val songs: List<Song> = emptyList(),
    val tvShows: List<TvShow> = emptyList(),
    val featureMovies: List<FeatureMovie> = emptyList(),
    val audioBooks: List<AudioBook> = emptyList(),
    val entries: List<ItemEntry> = emptyList()
)
