package com.headlessideas.http

import com.headlessideas.http.util.ok
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import java.io.DataOutputStream
import java.io.InputStream

class Response(var statusCode: StatusCode = ok, val headers: MutableList<Header> = mutableListOf(), var body: InputStream = ByteInputStream()) {

    override fun toString(): String {
        return "HTTP/1.0 $statusCode"
    }

    fun send(output: DataOutputStream) {
        output.writeBytes("HTTP/1.0 $statusCode \r\n")
        headers.forEach { output.writeBytes("$it\r\n") }
        output.writeBytes("\r\n")
        output.write(body.readBytes())
        output.writeBytes("\r\n")
        output.write("".toByteArray(), 0, 0)
    }
}

