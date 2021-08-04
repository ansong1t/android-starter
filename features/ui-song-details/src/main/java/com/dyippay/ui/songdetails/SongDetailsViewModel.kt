package com.dyippay.ui.songdetails

import androidx.lifecycle.viewModelScope
import com.dyippay.ReduxViewModel
import com.dyippay.domain.observers.ObserveAlbumSongs
import com.dyippay.domain.observers.ObserveRelatedSongs
import com.dyippay.domain.observers.ObserveSong
import com.dyippay.util.ObservableLoadingCounter
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SongDetailsViewModel @AssistedInject constructor(
    @Assisted trackId: Long,
    observeSong: ObserveSong,
    observeAlbumSongs: ObserveAlbumSongs,
    observeRelatedSongs: ObserveRelatedSongs
) : ReduxViewModel<SongDetailsViewState>(
    SongDetailsViewState()
) {

    private val songLoading = ObservableLoadingCounter()
    private val albumSongsLoading = ObservableLoadingCounter()
    private val songsRelatedLoading = ObservableLoadingCounter()

    init {
        viewModelScope.launch {
            songLoading.observable.collect { active ->
                setState { copy(isLoading = active) }
            }
        }
        viewModelScope.launch {
            songsRelatedLoading.observable.collect { active ->
                setState { copy(songsRelatedLoading = active) }
            }
        }
        viewModelScope.launch {
            albumSongsLoading.observable.collect { active ->
                setState { copy(albumSongsLoading = active) }
            }
        }

        viewModelScope.launch {
            observeSong.observe()
                .collect {
                    setState { copy(song = it) }
                    songLoading.removeLoader()

                    // get related songs after main song object received
                    observeAlbumSongs(ObserveAlbumSongs.Params(it.collectionId))

                    observeRelatedSongs(ObserveRelatedSongs.Params(it.id, it.artistId))
                }
        }
        observeSong(ObserveSong.Params(trackId))
        songLoading.addLoader()

        viewModelScope.launch {
            observeAlbumSongs.observe()
                .collect {
                    setState { copy(albumSongs = it) }
                    albumSongsLoading.removeLoader()
                }
        }
        albumSongsLoading.addLoader()

        viewModelScope.launch {
            observeRelatedSongs.observe()
                .collect {
                    setState { copy(relatedSongs = it) }
                    songsRelatedLoading.removeLoader()
                }
        }
        songsRelatedLoading.addLoader()
    }

    /**
     * Factory to allow assisted injection of [SongDetailsViewModel] with an initial state.
     */
    @AssistedFactory
    internal interface Factory {
        fun create(trackId: Long): SongDetailsViewModel
    }
}
