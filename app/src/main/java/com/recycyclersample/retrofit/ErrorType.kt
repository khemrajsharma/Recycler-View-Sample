package com.recycyclersample.retrofit

import com.recycyclersample.App.Companion.appRes
import com.recycyclersample.R

enum class ErrorType(var message: Int) {
    TokenExpire(R.string.error), Error(R.string.error), RequestTimeoutError(R.string.timeoutError), NoConnectionError(
        R.string.noConnectionError
    ),
    AuthFailureError(R.string.authFailureError), ServerError(R.string.serverError), NetworkError(R.string.networkError), BadRequestError(
        R.string.badRequestError
    ),
    ForbiddenError(R.string.forbiddenError), NotFoundError(R.string.notFoundError), UnsupportedMediaType(
        R.string.unsupportedMediaType
    ),
    MethodNotAllowedError(R.string.methodNotAllowedError), ParseError(R.string.parsing_error);

    fun getMessage(): String {
        return appRes!!.getString(message)
    }

    companion object {
        @JvmStatic
        fun getErrorTypeByVolleyError(errorCode: Int): ErrorType? {
            var errorType: ErrorType? = null
            errorType = when (errorCode) {
                400 -> BadRequestError
                401 -> AuthFailureError
                403 -> ForbiddenError
                404 -> NotFoundError
                408 -> RequestTimeoutError
                500, 501, 502, 503, 504, 505 -> ServerError
                else -> Error
            }
            return errorType
        }
    }
}