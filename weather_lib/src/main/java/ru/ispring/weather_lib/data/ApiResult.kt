package ru.ispring.weather_lib.data

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error(val errorType: ErrorType) : ApiResult<Nothing>
}

sealed interface ErrorType {
    data object NoInternet : ErrorType
    data object ServerConnectionError : ErrorType
    data object Unknown : ErrorType
}