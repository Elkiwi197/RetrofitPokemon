package com.example.retrofitpokemon.data.remote

sealed class NetworkResult<T>(
    var data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : NetworkResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : NetworkResult<T>(data, message)

    class Loading<T> : NetworkResult<T>()

}

inline fun <T, R> NetworkResult<T>.flatMap(transform: (T) -> NetworkResult<R>): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> transform(this.data!!)
        is NetworkResult.Error -> NetworkResult.Error(this.message)
        is NetworkResult.Loading -> NetworkResult.Loading()
    }
}

inline fun <T, R> NetworkResult<T>.map(transform: (T) -> R): NetworkResult<R> {
    return when (this) {
        is NetworkResult.Success -> NetworkResult.Success(transform(this.data!!))
        is NetworkResult.Error -> NetworkResult.Error(this.message)
        is NetworkResult.Loading -> NetworkResult.Loading()
    }
}

inline fun <T> NetworkResult<T>.onSuccess(action: (T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(this.data!!)
    return this
}

inline fun <T> NetworkResult<T>.onError(action: (String?) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) action(this.message)
    return this
}
