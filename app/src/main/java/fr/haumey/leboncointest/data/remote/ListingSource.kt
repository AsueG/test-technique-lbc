package fr.haumey.leboncointest.data.remote

import fr.haumey.leboncointest.data.repository.BaseRepository
import javax.inject.Inject

class ListingSource @Inject constructor(private val api: ListingService) : BaseRepository() {

    suspend fun getTitles() = apiCall { api.getTitles() }
}