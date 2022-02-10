package fr.haumey.leboncointest.model

data class State<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> loading(data: T? = null): State<T> {
            return State(Status.LOADING, data, null)
        }

        fun <T> success(data: T): State<T> {
            return State(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T? = null): State<T> {
            return State(Status.ERROR, data, message)
        }
    }
}