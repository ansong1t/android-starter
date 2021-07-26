package com.dyippay.data.datasources

import com.dyippay.data.entities.ErrorResult
import com.dyippay.data.entities.Result
import com.dyippay.data.responses.ItemResponse
import com.dyippay.data.services.ItemService
import com.dyippay.extensions.toResult
import javax.inject.Inject

class GetItemDataSource @Inject constructor(
    private val itemService: ItemService
) {
    suspend operator fun invoke(): Result<List<ItemResponse>> =
        try {
            itemService.getItems().toResult { response ->
                response.results!!
            }
        } catch (e: Exception) {
            ErrorResult(e)
        }
}
