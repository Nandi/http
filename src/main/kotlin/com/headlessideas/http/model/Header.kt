package com.headlessideas.http.model

data class Header(val name: String, val value: String) {
    override fun toString(): String {
        return "$name: $value"
    }
}