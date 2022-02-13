package fr.haumey.leboncointest

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.haumey.leboncointest.utils.isVisible
import fr.haumey.leboncointest.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ListingFragmentAndroidTest {

    private val context
        get() = InstrumentationRegistry.getInstrumentation().targetContext

    private val resources
        get() = context.resources

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun listingFragmentAndroidTest() {
        launchActivity<MainActivity>().use {
            sleep(4000)
            displayPhotoWhenUserClickOnTitle()
        }
    }

    private fun displayPhotoWhenUserClickOnTitle() {
        onView(withId(R.id.recyclerViewListing)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        sleep()

        onView(withId(R.id.imageViewBig)).isVisible()
        sleep()
    }

    private fun sleep(millis: Long = 2000) {
        Thread.sleep(millis)
    }
}