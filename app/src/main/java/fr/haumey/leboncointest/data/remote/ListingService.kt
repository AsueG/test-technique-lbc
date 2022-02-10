package fr.haumey.leboncointest.data.remote

import fr.haumey.leboncointest.model.Title
import retrofit2.Response
import retrofit2.http.GET

interface ListingService {

    @GET("img/shared/technical-test.json")
    suspend fun getTitles(): Response<List<Title>>
}