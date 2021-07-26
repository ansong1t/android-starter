package com.dyippay.data.mappers

import com.dyippay.data.entities.TvShow
import com.dyippay.data.responses.ItemResponse
import javax.inject.Inject

class ItemResponseToTvShowMapper @Inject constructor() : Mapper<ItemResponse, TvShow> {
    override suspend operator fun invoke(from: ItemResponse): TvShow =
        TvShow(
            id = from.collectionId,
            artworkUrl100 = from.artworkUrl100,
            collectionCensoredName = from.collectionCensoredName,
            collectionExplicitness = from.collectionExplicitness,
            collectionName = from.collectionName,
            collectionPrice = from.collectionPrice,
            collectionViewUrl = from.collectionViewUrl,
            contentAdvisoryRating = from.contentAdvisoryRating,
            currency = from.currency,
            discCount = from.discCount,
            discNumber = from.discNumber,
            collectionHdPrice = from.collectionHdPrice,
            primaryGenreName = from.primaryGenreName,
            longDescription = from.longDescription
        )
}
