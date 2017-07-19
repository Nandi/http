package com.headlessideas.http

import com.headlessideas.http.util.*
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.Socket
import java.nio.file.Paths

suspend fun handleClient(client: Socket, documentRoot: String) {
    val input = BufferedReader(InputStreamReader(client.getInputStream()))
    val output = DataOutputStream(client.getOutputStream())

    val response = handleRequest(input, documentRoot)
    response.writeResponse(output)
    input.close()
    output.close()
    client.close()
}

private fun handleRequest(input: BufferedReader, documentRoot: String): Response {
    val requestLine = input.readLine()
    val headers = mutableListOf<Header>()
    var s = input.readLine()
    while (s != null && s.isNotEmpty()) {
        val (name, value) = s.split(": ", limit = 2)
        headers.add(Header(name, value))
        s = input.readLine()
    }

    val (method, path, httpVersion) = requestLine.split(" ")

    if (!validHttpVersion(httpVersion)) {
        return Response(httpVersionNotSupported)
    }
    if (!validMethod(method)) {
        return Response(methodNotAllowed)
    }
    if (!validFile(documentRoot, path)) {
        return Response(notFound)
    }

    val file = Paths.get(documentRoot, path).toFile()
    val contentType = getContentType(file)


    if (Method.valueOf(method) == Method.HEAD) {
        return Response(ok, listOf(contentType), null)
    } else {
        return Response(ok, listOf(contentType), file)
    }
}


private val validVersions = listOf("HTTP/1.0", "HTTP/1.1")

private fun validHttpVersion(httpVersion: String): Boolean = validVersions.contains(httpVersion)

private fun validMethod(method: String): Boolean = Method.values().any { it.name == method }

private fun validFile(documentRoot: String, path: String): Boolean {
    val file = Paths.get(documentRoot, path).toFile()
    return file.exists() && file.isFile
}

