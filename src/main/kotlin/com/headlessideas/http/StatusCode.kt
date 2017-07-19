package com.headlessideas.http

data class StatusCode(val code: Int, val message: String) {
    override fun toString(): String {
        return "$code $message"
    }
}