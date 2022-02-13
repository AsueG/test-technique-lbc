package fr.haumey.leboncointest.view.listing

import android.content.res.Configuration
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.haumey.leboncointest.R
import fr.haumey.leboncointest.model.State
import fr.haumey.leboncointest.utils.hide
import fr.haumey.leboncointest.utils.show
import fr.haumey.leboncointest.view.photo.PhotoFragment
import fr.haumey.leboncointest.viewmodel.ListingViewModel

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    private val listingViewModel: ListingViewModel by viewModels()

    private lateinit var viewAdapter: ListingAdapter
    private lateinit var viewManager: GridLayoutManager

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        viewManager.spanCount = requireContext().resources.getInteger(R.integer.span_count)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewManager = GridLayoutManager(requireContext(), requireContext().resources.getInteger(R.integer.span_count))

        viewAdapter = ListingAdapter {
            val action = ListingFragmentDirections.actionListingFragmentToPhotoFragment(it.url)
            findNavController().navigate(action)
        }

        view.findViewById<RecyclerView>(R.id.recyclerViewListing).apply {
            adapter = viewAdapter
            layoutManager = viewManager
        }

        observer()
    }

    private fun observer() {
        listingViewModel.titles.observe(viewLifecycleOwner) { state ->
            when (state.status) {
                State.Status.LOADING -> view?.findViewById<ProgressBar>(R.id.progressBarListing)?.show()
                State.Status.SUCCESS -> {
                    view?.findViewById<ProgressBar>(R.id.progressBarListing)?.hide()

                    state.data?.let {
                        viewAdapter.differ.submitList(it)
                    }
                }
                State.Status.ERROR -> view?.findViewById<ProgressBar>(R.id.progressBarListing)?.hide()
            }
        }
    }
}