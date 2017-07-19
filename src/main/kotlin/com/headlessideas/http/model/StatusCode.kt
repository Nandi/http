package com.headlessideas.http.model

data class StatusCode(val code: Int, val message: String) {
    override fun toString(): String {
        return "$code $message"
    }
}

val notFound = StatusCode(404, "Not Found")
val ok = StatusCode(200, "OK")
val forbidden = StatusCode(403, "Forbidden")
val internalServerError = StatusCode(500, "Internal Server Error")