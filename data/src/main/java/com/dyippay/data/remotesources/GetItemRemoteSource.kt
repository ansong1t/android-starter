package com.dyippay.data.remotesources

import com.dyippay.data.entities.AudioBook
import com.dyippay.data.entities.ErrorResult
import com.dyippay.data.entities.FeatureMovie
import com.dyippay.data.entities.ItemEntry
import com.dyippay.data.entities.Result
import com.dyippay.data.entities.Song
import com.dyippay.data.entities.TvEpisode
import com.dyippay.data.entities.TvShow
import com.dyippay.data.mappers.ItemResponseToAudioBookMapper
import com.dyippay.data.mappers.ItemResponseToFeatureMovieMapper
import com.dyippay.data.mappers.ItemResponseToSongMapper
import com.dyippay.data.mappers.ItemResponseToTvEpisodeMapper
import com.dyippay.data.mappers.ItemResponseToTvShowMapper
import com.dyippay.data.services.ItemService
import com.dyippay.data.types.ListItemType
import com.dyippay.data.types.responseToTrackType
import com.dyippay.extensions.toResult
import javax.inject.Inject

class GetItemRemoteSource @Inject constructor(
    private val itemService: ItemService,
    private val songMapper: ItemResponseToSongMapper,
    private val featureMovieMapper: ItemResponseToFeatureMovieMapper,
    private val tvEpisodeMapper: ItemResponseToTvEpisodeMapper,
    private val tvShowMapper: ItemResponseToTvShowMapper,
    private val audioBookMapper: ItemResponseToAudioBookMapper
) {
    suspend operator fun invoke(): Result<AllItems> =
        try {
            itemService.getItems().toResult { response ->
                val results = response.data

                val tvEpisodes = arrayListOf<TvEpisode>()
                val songs = arrayListOf<Song>()
                val tvShows = arrayListOf<TvShow>()
                val featureMovies = arrayListOf<FeatureMovie>()
                val audioBooks = arrayListOf<AudioBook>()
                val entries = arrayListOf<ItemEntry>()

                results.forEach {
                    when (responseToTrackType(it.kind)) {
                        ListItemType.SONG -> songs.add(songMapper(it))
                        ListItemType.FEATURE_MOVIE -> featureMovies.add(featureMovieMapper(it))
                        ListItemType.TV_SHOW -> {
                            tvShows.add(tvShowMapper(it))
                            tvEpisodes.add(tvEpisodeMapper(it))
                        }
                        else -> {
                            if (it.wrapperType == "audiobook") {
                                audioBooks.add(audioBookMapper(it))
                            }
                        }
                    }
                    val entry = ItemEntry(
                        itemId = when (it.wrapperType) {
                            "track" -> when (responseToTrackType(it.kind)) {
                                ListItemType.TV_SHOW, ListItemType.AUDIOBOOK -> it.collectionId
                                else -> it.trackId
                            }
                            "audiobook" -> it.collectionId
                            else -> it.trackId
                        },
                        kind = when (it.wrapperType) {
                            "track" -> responseToTrackType(it.kind)
                            "audiobook" -> ListItemType.AUDIOBOOK
                            else -> ListItemType.UNKNOWN
                        }
                    )
                    entries.add(entry)
                }

                AllItems(
                    tvEpisodes, songs, tvShows, featureMovies, audioBooks, entries
                )
            }
        } catch (e: Exception) {
            ErrorResult(e)
        }
}
