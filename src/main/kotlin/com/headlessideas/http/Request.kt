package com.headlessideas.http

import java.io.BufferedReader
import java.net.InetSocketAddress

class Request(val httpVersion: String,
              val method: Method = Method.GET,
              val path: String,
              val headers: List<Header>,
              val body: String? = null,
              var clientAddress: InetSocketAddress? = null) {
    override fun toString(): String {
        return "${clientAddress?.address} $httpVersion $method $path"
    }

    companion object {
        fun fromStream(input: BufferedReader): Request {
            val requestLine = input.readLine()

            val (method, path, httpVersion) = requestLine.split(" ")

            val headers = mutableListOf<Header>()
            var body: String? = null
            var s = input.readLine()
            while (s != null && s.isNotEmpty()) {
                val (name, value) = s.split(": ", limit = 2)
                headers.add(Header(name, value))
                s = input.readLine()
            }

            //if there is a content length header, we need to read the body from the input
            val contentLength = headers.find { it.name.equals("content-length", true) }
            if (contentLength != null) {
                val charArray = CharArray(contentLength.value.toInt())
                input.read(charArray)
                body = charArray.toString()
            }

            return Request(httpVersion, Method.get(method), checkPath(path), headers, body)
        }

        private fun checkPath(path: String): String = path.takeUnless { it == "/" } ?: path + "index.html"
    }
}