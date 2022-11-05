package com.wajahat.networkcallsinadapter.utils

import com.wajahat.networkcallsinadapter.utils.Status.*

/**
 * A generic class that represents the API response state
 * */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {

        /** If API succeeds, this method returns the response
         * @param data Response data returned from the API
         * */
        fun <T> success(data: T): Resource<T> = Resource(status = SUCCESS, data = data, message = null)

        /** If API fails, this method returns the error object
         * @param data Error block(if any) returned from the API
         * @param message Error message(if any) returned from the API
         * */
        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = ERROR, data = data, message = message)

        /** To notify the user that data is loading, we emit this status to show some progressbar etc.
         * @param data Any (optional) data
         * */
        fun <T> loading(data: T?): Resource<T> = Resource(status = LOADING, data = data, message = null)
    }
}