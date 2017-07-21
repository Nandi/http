package com.headlessideas.http

import java.io.BufferedReader
import java.net.SocketAddress

class Request(val httpVersion: String,
              val method: Method = Method.GET,
              val path: String,
              val headers: List<Header>,
              var clientAddress: SocketAddress? = null) {
    override fun toString(): String {
        return "[$clientAddress] $httpVersion $method $path"
    }

    companion object {
        fun fromStream(input: BufferedReader): Request {
            val requestLine = input.readLine()

            val (method, path, httpVersion) = requestLine.split(" ")

            val headers = mutableListOf<Header>()
            var s = input.readLine()
            while (s != null && s.isNotEmpty()) {
                val (name, value) = s.split(": ", limit = 2)
                headers.add(Header(name, value))
                s = input.readLine()
            }

            return Request(httpVersion, Method.get(method), checkPath(path), headers)
        }

        private fun checkPath(path: String): String = path.takeUnless { it == "/" } ?: path + "index.html"
    }
}