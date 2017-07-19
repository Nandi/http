package com.headlessideas.http

import java.io.DataOutputStream
import java.io.File

class Response(val statusCode: StatusCode, val headers: List<Header> = listOf(), val file: File? = null) {
    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("HTTP/1.0 ").append(statusCode).append("\r\n")
        headers.forEach { sb.append(it).append("\r\n") }
        return sb.toString()
    }

    fun writeResponse(output: DataOutputStream) {
        output.writeBytes("HTTP/1.0 $statusCode \r\n")
        headers.forEach { output.writeBytes("$it\r\n") }
        output.writeBytes("\r\n")
        file?.let {
            output.write(it.readBytes())
        }
        output.writeBytes("\r\n")
        output.write("".toByteArray(), 0, 0)
    }
}

