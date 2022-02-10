package fr.haumey.leboncointest.data.repository

import fr.haumey.leboncointest.model.State
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T> apiCall(call: suspend () -> Response<T>): State<T> {
        try {
            val response = call()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) return State.success(body)
            }

            return error("${response.code()} ${response.message()}")
        }
        catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): State<T> {
        return State.error("Network call failed: $message")
    }

}