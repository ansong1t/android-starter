package com.dyippay.domain.interactors

import com.dyippay.data.daos.ItemDao
import com.dyippay.data.repositories.ItemRepository
import com.dyippay.domain.Interactor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateItems @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val repository: ItemRepository,
    private val itemDao: ItemDao
) : Interactor<UpdateItems.Params>() {

    data class Params(val page: Page, val forceRefresh: Boolean = false)

    enum class Page {
        NEXT_PAGE, REFRESH
    }

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
            if (params.page == Page.REFRESH) {
                if (params.forceRefresh) {
                    itemDao.clearItemEntries()
                    repository.updateItems()
                } else if (!params.forceRefresh && !itemDao.hasItemEntry()) {
                    repository.updateItems()
                }
            } else if (params.page == Page.NEXT_PAGE) {
                // no action for now until API endpoint supports pagination
            }
        }
    }
}
