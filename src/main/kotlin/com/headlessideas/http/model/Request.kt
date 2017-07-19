package com.headlessideas.http.model

class Request(val method: Method, val path: String, val headers: List<Header>) {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append(method).append(" ").append(path).append(" HTTP/1.0\r\n")
        headers.forEach { sb.append(it).append("\r\n") }
        return sb.toString()
    }
}