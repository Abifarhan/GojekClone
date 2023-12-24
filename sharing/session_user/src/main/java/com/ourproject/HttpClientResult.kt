

sealed class HttpClientResult<T>{

    data class Success<T>(val root: T) : HttpClientResult<T>()

    data class Failure<T>(val throwable: Throwable) : HttpClientResult<T>()
}