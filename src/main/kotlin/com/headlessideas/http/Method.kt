package com.headlessideas.http

enum class Method {
    GET, HEAD, UNKNOWN;

    companion object {
        fun get(value: String): Method {
            if (values().any { it.name == value }) {
                return Method.valueOf(value)
            } else {
                return UNKNOWN
            }
        }

    }
}