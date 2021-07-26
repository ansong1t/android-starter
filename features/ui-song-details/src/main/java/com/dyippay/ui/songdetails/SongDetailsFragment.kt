package com.dyippay.ui.songdetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dyippay.common.FragmentWithBinding
import com.dyippay.common.navigation.defaultNavAnimation
import com.dyippay.common.navigation.songDetailsDeeplink
import com.dyippay.ui.SpacingItemDecorator
import com.dyippay.ui.songdetails.databinding.FragmentSongDetailsBinding
import com.dyippay.util.viewModelProviderFactoryOf
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applySystemWindowInsetsToPadding
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SongDetailsFragment :
    FragmentWithBinding<FragmentSongDetailsBinding>() {

    @Inject
    internal lateinit var vmFactory: SongDetailsViewModel.Factory

    private val args: SongDetailsFragmentArgs by navArgs()

    private val viewModel: SongDetailsViewModel by viewModels {
        viewModelProviderFactoryOf {
            vmFactory.create(args.trackId)
        }
    }

    private var controller: SongDetailsEpoxyController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = SongDetailsEpoxyController(requireContext())
    }

    override fun onViewCreated(
        binding: FragmentSongDetailsBinding,
        savedInstanceState: Bundle?
    ) {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.ivHeader.applySystemWindowInsetsToPadding(top = true, consume = true)
        binding.parent.setEdgeToEdgeSystemUiFlags(true)
        binding.rvScrollable.apply {
            setController(controller!!.apply {
                callbacks = object : SongDetailsEpoxyController.Callbacks {
                    override fun onPreviewUrl(url: String) {
                        startActivity(Intent(Intent.ACTION_VIEW).apply {
                            data = url.toUri()
                        })
                    }

                    override fun onTrackClicked(trackId: Long) {
                        findNavController().navigate(
                            songDetailsDeeplink(trackId),
                            defaultNavAnimation()
                        )
                    }
                }
            })
            addItemDecoration(
                SpacingItemDecorator(
                    resources.getDimension(R.dimen.space_small).toInt()
                )
            )
        }

        viewModel.liveData.observe(viewLifecycleOwner, ::render)
    }

    override fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSongDetailsBinding =
        FragmentSongDetailsBinding.inflate(inflater, container, false)

    override fun onDestroy() {
        binding?.rvScrollable?.clear()
        super.onDestroy()
        controller?.clear()
        controller = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        controller?.removeCallback()
    }

    private fun render(state: SongDetailsViewState) {
        val binding = requireBinding()
        binding.state = state
        controller?.state = state
    }
}
