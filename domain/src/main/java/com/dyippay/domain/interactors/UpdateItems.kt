package com.dyippay.domain.interactors

import com.dyippay.data.fetch
import com.dyippay.data.repositories.items.ItemsStore
import com.dyippay.domain.Interactor
import com.dyippay.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateItems @Inject constructor(
    private val appCoroutineDispatchers: AppCoroutineDispatchers,
    private val itemsStore: ItemsStore
) : Interactor<UpdateItems.Params>() {

    data class Params(val page: Int, val forceRefresh: Boolean = false)

    override suspend fun doWork(params: Params) {
        withContext(appCoroutineDispatchers.io) {
//            val page = when {
//                params.page >= 0 -> params.page
//                params.page == Page.NEXT_PAGE -> {
//                    val lastPage = itemDao.getLastPage()
//                    if (lastPage != null) lastPage + 1 else 0
//                }
//                else -> 0
//            }

            itemsStore.fetch("items", params.forceRefresh)
        }
    }

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}
