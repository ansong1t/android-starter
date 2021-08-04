package com.dyippay.common.paging

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.dyippay.common.epoxy.TotalSpanOverride
import com.dyippay.common.layout.vertSpacerSmall
import com.dyippay.extensions.observable
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
abstract class PagingEpoxyController<STATE, LI : Any, Placeholder : EpoxyModel<*>>(
    initialState: STATE,
    clearCacheOnStateChange: Boolean = false
) : PagingDataEpoxyController<LI>() {

    var state: STATE by observable(initialState) {
        if (clearCacheOnStateChange) requestForcedModelBuild()
        else requestForcedModelBuild()
    }

    @Suppress("UselessCallOnCollection")
    override fun addModels(models: List<EpoxyModel<*>>) {

        insertHeaderModels(this)

        if (models.isNotEmpty()) {
            vertSpacerSmall {
                id("top_spacer")
                spanSizeOverride(TotalSpanOverride)
                onBind { _, view, _ ->
                    val layoutParams =
                        view.dataBinding.root.layoutParams as? StaggeredGridLayoutManager.LayoutParams
                    layoutParams?.isFullSpan = true
                }
            }

            super.addModels(models)

            vertSpacerSmall {
                id("bottom_spacer")
                spanSizeOverride(TotalSpanOverride)
                onBind { _, view, _ ->
                    val layoutParams =
                        view.dataBinding.root.layoutParams as? StaggeredGridLayoutManager.LayoutParams
                    layoutParams?.isFullSpan = true
                }
            }
        } else {
            onEmptyState(this)
        }

        insertFooterModels(this)
    }

    override fun buildItemModel(currentPosition: Int, item: LI?): EpoxyModel<*> {
        return if (item != null) buildItemModel(item) else buildItemPlaceholder(currentPosition)
    }

    protected abstract fun buildItemModel(item: LI): EpoxyModel<*>

    protected open fun onEmptyState(modelCollector: ModelCollector) {}

    protected open fun insertHeaderModels(modelCollector: ModelCollector) {}

    protected open fun insertFooterModels(modelCollector: ModelCollector) {}

    protected abstract fun buildItemPlaceholder(index: Int): Placeholder
}
