package dev.mamkin.notemark.core.presentation.util


import android.content.Context
import dev.mamkin.notemark.R
import dev.mamkin.notemark.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when(this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
        NetworkError.BAD_REQUEST -> R.string.error_unknown
        NetworkError.UNAUTHORIZED -> R.string.error_unknown
        NetworkError.METHOD_NOT_ALLOWED -> R.string.error_unknown
        NetworkError.CONFLICT -> R.string.error_unknown
    }
    return context.getString(resId)
}