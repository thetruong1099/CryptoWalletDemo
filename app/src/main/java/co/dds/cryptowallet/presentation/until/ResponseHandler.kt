package co.dds.cryptowallet.presentation.until

import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException

enum class ErrorCodes(val code: Int) {
    SocketTimeOut(-1)
}

class ResponseHandler() {
    fun <T : Any> handlerSuccess(data: T): Resource<T> = Resource.success(data)

    fun <T : Any> handlerException(e: Exception): Resource<T> = when (e) {
        is HttpException -> Resource.error(getErrorMessage(e.code()), null)
        is SocketTimeoutException -> Resource.error(
            getErrorMessage(ErrorCodes.SocketTimeOut.code),
            null
        )
        else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            ErrorCodes.SocketTimeOut.code -> "Timeout"
            400 -> "Bad Request. Validation failed for the given object in the HTTP Body or Request parameters"
            401 -> " Unauthorized. Not valid or inactive subscription key present in the HTTP Header"
            403 -> "Forbidden. The request is authenticated, but it is not possible to perform the required operation due to a logical error or invalid permissions"
            404 -> "Not found"
            500 -> "Internal server error. There was an error on the server while processing the request"
            else -> "Something went wrong"
        }
    }
}