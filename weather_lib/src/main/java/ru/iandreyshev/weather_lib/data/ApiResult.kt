package ru.iandreyshev.weather_lib.data

sealed interface ApiResult<out T> {
    class Success<T>(val data: T) : ApiResult<T>
    class Error(val errorType: ErrorType) : ApiResult<Nothing>
}

sealed interface ErrorType {
    data object NoInternet : ErrorType
    data object ServerConnectionError : ErrorType
    data object Unknown : ErrorType
}