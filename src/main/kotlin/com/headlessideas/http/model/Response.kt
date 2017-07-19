package com.headlessideas.http.model

import java.io.File

class Response(val statusCode: StatusCode, val headers: List<Header>, val file: File) {
    override fun toString(): String {
        val sb = StringBuilder()

        sb.append("HTTP/1.0 ").append(statusCode).append("\r\n")
        headers.forEach { sb.append(it).append("\r\n") }
        sb.append("\r\n")
        //todo: temp response body
        sb.append("<H1>Success!</H1>\r\n")
        sb.append("\r\n")

        return sb.toString()
    }
}