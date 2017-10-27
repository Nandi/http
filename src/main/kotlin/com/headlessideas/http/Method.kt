package com.headlessideas.http

enum class Method {
    GET, HEAD, POST, UNKNOWN;

    companion object {
        fun get(value: String): Method =
                if (values().any { it.name == value }) {
                    Method.valueOf(value)
                } else {
                    UNKNOWN
                }

    }
}