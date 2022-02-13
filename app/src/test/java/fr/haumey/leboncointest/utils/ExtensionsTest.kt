package fr.haumey.leboncointest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValuesTest(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    maxCountDown: Int = 1,
    afterObserve: () -> Unit = {}
): List<T?> {
    val data: MutableList<T?> = mutableListOf()
    val latch = CountDownLatch(maxCountDown)

    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data.add(o)
            latch.countDown()
            if (latch.count == 0L) {
                this@getOrAwaitValuesTest.removeObserver(this)
            }
        }
    }

    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

    }
    finally {
        this.removeObserver(observer)
    }

    return data.toList()
}