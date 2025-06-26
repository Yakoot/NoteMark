package dev.mamkin.notemark.core.presentation.util


import android.content.Context
import dev.mamkin.notemark.R
import dev.mamkin.notemark.core.domain.util.DataError

fun DataError.Network.toString(context: Context): String {
    val resId = when(this) {
        DataError.Network.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Network.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        DataError.Network.NO_INTERNET -> R.string.error_no_internet
        DataError.Network.SERVER_ERROR -> R.string.error_unknown
        DataError.Network.SERIALIZATION -> R.string.error_serialization
        DataError.Network.UNKNOWN -> R.string.error_unknown
        DataError.Network.BAD_REQUEST -> R.string.error_unknown
        DataError.Network.UNAUTHORIZED -> R.string.error_unknown
        DataError.Network.METHOD_NOT_ALLOWED -> R.string.error_unknown
        DataError.Network.CONFLICT -> R.string.error_unknown
    }
    return context.getString(resId)
}
