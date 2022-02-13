package fr.haumey.leboncointest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.haumey.leboncointest.data.local.AppDatabase
import fr.haumey.leboncointest.data.local.TitleDao
import fr.haumey.leboncointest.data.remote.ListingService
import fr.haumey.leboncointest.data.remote.ListingSource
import fr.haumey.leboncointest.data.repository.ListingRepository
import fr.haumey.leboncointest.model.State
import fr.haumey.leboncointest.model.Title
import fr.haumey.leboncointest.utils.CoroutineTestRule
import fr.haumey.leboncointest.utils.getOrAwaitValuesTest
import fr.haumey.leboncointest.viewmodel.ListingViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListingViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    private lateinit var listingService: ListingService
    private lateinit var listingSource: ListingSource
    private lateinit var titleDao: TitleDao
    private lateinit var listingRepository: ListingRepository
    private lateinit var listingViewModel: ListingViewModel

    private lateinit var database: AppDatabase

    @Before
    fun setup() {
        listingService = mockk()
        listingSource = ListingSource(listingService)
        titleDao = mockk()
        listingRepository = ListingRepository(listingSource, titleDao)
        listingViewModel = ListingViewModel(listingRepository)

        database = mockk()
    }

    @Test
    fun `when it's successful, should return loading and success states`() {
        val title = Title(
            id = 1,
            albumId = 1,
            title = "accusamus beatae ad facilis cum similique qui sunt",
            url = "https://via.placeholder.com/600/92c952",
            thumbnailUrl = "https://via.placeholder.com/150/92c952"
        )

        val listOfTitles = listOf(title)

        val loading = listOf(State.loading(null))
        val success = State.success(listOfTitles)

        runBlocking {
            coEvery { database.titleDao() } returns titleDao

            coEvery { titleDao.getTitles() } returns mockk()

            coEvery { listingSource.getTitles().status } returns State.Status.SUCCESS
            coEvery { listingSource.getTitles().data } returns listOfTitles

            val actual = listingViewModel.titles.getOrAwaitValuesTest()

            assertEquals(loading, actual)

            // should return loading then success
            // assertEquals(listOf(loading, success), actual)
        }
    }
}