package fr.haumey.leboncointest.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.haumey.leboncointest.data.repository.ListingRepository
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(private val repository: ListingRepository) : ViewModel() {

    val titles = repository.getTitles()
}