package fr.haumey.leboncointest.data.repository

import fr.haumey.leboncointest.data.local.TitleDao
import fr.haumey.leboncointest.data.remote.ListingSource
import fr.haumey.leboncointest.utils.performOperation
import javax.inject.Inject

class ListingRepository @Inject constructor(private val remoteDataSource: ListingSource, private val localDataSource: TitleDao) {

    fun getTitles() = performOperation(
        databaseQuery = { localDataSource.getTitles() },
        networkCall = { remoteDataSource.getTitles() },
        saveCallResult = { localDataSource.insertTitles(it) }
    )
}
