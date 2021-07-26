package com.dyippay.domain.interactors

import com.dyippay.data.daos.ItemDao
import com.dyippay.data.repositories.ItemRepository
import com.dyippay.domain.Interactor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateSearchedItems @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: ItemRepository,
    private val itemDao: ItemDao
) : Interactor<UpdateSearchedItems.Params>() {

    data class Params(val page: Page, val searchKey: String)

    enum class Page {
        NEXT_PAGE, REFRESH
    }

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
            if (params.page == Page.REFRESH) {
//                itemDao.clearSearchResults()
                if (params.searchKey.isNotEmpty()) repository.searchItems(params.searchKey)
            } else if (params.page == Page.NEXT_PAGE) {
                // no action for now until API endpoint supports pagination
            }
        }
    }
}
