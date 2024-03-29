package com.dyippay.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.ModelCollector
import com.dyippay.common.FragmentWithBinding
import com.dyippay.common.epoxy.TotalSpanOverride
import com.dyippay.common.layout.headline3
import com.dyippay.common.layout.infiniteLoading
import com.dyippay.common.navigation.defaultNavAnimation
import com.dyippay.common.navigation.songDetailsDeeplink
import com.dyippay.common.paging.PagingEpoxyController
import com.dyippay.data.qualifiers.LastUserVisitFormatter
import com.dyippay.data.resultentities.ItemEntryWithDetails
import com.dyippay.data.types.ListItemType
import com.dyippay.ui.ProgressTimeLatch
import com.dyippay.ui.SpacingItemDecorator
import com.dyippay.ui.items.databinding.FragmentItemsBinding
import com.dyippay.util.autoCleared
import com.dyippay.util.getLastUserVisitedTime
import com.dyippay.util.getPref
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.text.SimpleDateFormat
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class ItemsFragment : FragmentWithBinding<FragmentItemsBinding>() {

    private val viewModel: ItemsViewModel by viewModels()

    @LastUserVisitFormatter
    @Inject
    lateinit var dateFormatter: SimpleDateFormat

    private var swipeRefreshLatch: ProgressTimeLatch by autoCleared()
    private var controller: PagingEpoxyController<
            ItemsViewState,
            ItemEntryWithDetails,
            ItemBindingModel_>? = null

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentItemsBinding {
        return FragmentItemsBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = createController()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller = null
    }

    override fun onDestroyView() {
        binding?.rvItemLists?.clear()
        super.onDestroyView()
    }

    override fun onViewCreated(
        binding: FragmentItemsBinding,
        savedInstanceState: Bundle?
    ) {
//        binding!!.rvItemLists.applySystemWindowInsetsToMargin(top = true)
        swipeRefreshLatch = ProgressTimeLatch {
            binding.refresher.isRefreshing = it
        }

        binding.rvItemLists.apply {
            setController(controller!!)
            addItemDecoration(
                SpacingItemDecorator(
                    resources.getDimension(R.dimen.padding_8).toInt()
                )
            )
        }

        binding.refresher.setOnRefreshListener(viewModel::refresh)

        lifecycleScope.launchWhenStarted {
            viewModel.pagedList.collect {
                controller?.submitData(it)
            }
        }

        viewModel.liveData.observe(viewLifecycleOwner, ::render)
    }

    private fun render(state: ItemsViewState) {
        controller!!.state = state

        state.error?.let { error ->
            swipeRefreshLatch.refreshing = false
            Snackbar.make(requireView(), error.message, Snackbar.LENGTH_SHORT).show()
        }
        swipeRefreshLatch.refreshing = state.isLoading
    }

    @ObsoleteCoroutinesApi
    private fun createController(): PagingEpoxyController<
            ItemsViewState, ItemEntryWithDetails, ItemBindingModel_> =
        object :
            PagingEpoxyController<ItemsViewState, ItemEntryWithDetails, ItemBindingModel_>(
                ItemsViewState(),
                true
            ) {

            override fun insertHeaderModels(modelCollector: ModelCollector) {
                with(modelCollector) {
                    headline3 {
                        id("headline")
                        val lastTimeVisited = requireContext().getPref().getLastUserVisitedTime()
                        val text = String.format(
                            getString(R.string.last_date_previously_visited),
                            if (lastTimeVisited != null) dateFormatter.format(
                                lastTimeVisited
                            ) else ""
                        )
                        text(text)
                        onBind { _, view, _ ->
                            val layoutParams =
                                view.dataBinding.root.layoutParams as? StaggeredGridLayoutManager.LayoutParams
                            layoutParams?.isFullSpan = true
                        }
                    }
                }
            }

            override fun buildItemModel(item: ItemEntryWithDetails): EpoxyModel<*> {
                return ItemBindingModel_()
                    .id(item.generateStableId())
                    .name(item.trackName())
                    .imageUrl(item.imageUrl())
                    .genre(item.genre())
                    .price(item.trackPrice())
                    .currency(item.currency())
                    .kind(item.kind())
                    .onClickListener(View.OnClickListener {
                        findNavController().navigate(
                            when (item.itemEntry.kind) {
                                ListItemType.TV_SHOW -> songDetailsDeeplink(item.itemEntry.itemId)
                                ListItemType.SONG -> songDetailsDeeplink(item.itemEntry.itemId)
                                ListItemType.FEATURE_MOVIE -> songDetailsDeeplink(item.itemEntry.itemId)
                                else -> songDetailsDeeplink(item.itemEntry.itemId)
                            }, defaultNavAnimation()
                        )
                    })
            }

            override fun buildItemPlaceholder(index: Int): ItemBindingModel_ =
                ItemBindingModel_()
                    .id("item_placeholder_$index")

            override fun insertFooterModels(modelCollector: ModelCollector) {
                with(modelCollector) {
                    if (controller?.state?.isLoading == true) {
                        infiniteLoading {
                            id("loading_view")
                            spanSizeOverride(TotalSpanOverride)
                            onBind { _, view, _ ->
                                val layoutParams =
                                    view.dataBinding.root.layoutParams as? StaggeredGridLayoutManager.LayoutParams
                                layoutParams?.isFullSpan = true
                            }
                        }
                    }
                }
            }
        }
}
