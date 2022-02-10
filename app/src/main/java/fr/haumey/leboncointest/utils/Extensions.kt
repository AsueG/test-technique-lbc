package fr.haumey.leboncointest.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import fr.haumey.leboncointest.model.State
import kotlinx.coroutines.Dispatchers

fun <T, A> performOperation(databaseQuery: () -> LiveData<T>, networkCall: suspend () -> State<A>, saveCallResult: suspend (A) -> Unit): LiveData<State<T>> =
    liveData(Dispatchers.IO) {
        emit(State.loading())

        val source = databaseQuery.invoke().map { State.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()

        if (responseStatus.status == State.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
        }
        else if (responseStatus.status == State.Status.ERROR) {
            emit(State.error(responseStatus.message))
            emitSource(source)
        }
    }