package com.headlessideas.http

import com.headlessideas.http.util.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Server(val documentRoot: Path, val port: Int) {

    val httpVersions = setOf("HTTP/1.0", "HTTP/1.1")

    fun serve(): Nothing {

        val socket = ServerSocket(port)
        println("Listening on port $port")

        while (true) {
            val client = socket.accept()
            launch(CommonPool) {
                try {
                    client.use {
                        handleClient(client)
                    }
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
    }

    suspend fun handleClient(client: Socket) {
        val input = BufferedReader(InputStreamReader(client.getInputStream()))
        val output = DataOutputStream(client.getOutputStream())

        val request = Request.fromStream(input)
        request.clientAddress = client.remoteSocketAddress
        println(request)
        val response = Response()

        try {
            handleRequest(request, response)
        } catch (e: Exception) {
            response.statusCode = internalServerError
            println(e)
        }

        response.send(output)
    }

    private fun handleRequest(request: Request, response: Response) {
        if (!httpVersions.contains(request.httpVersion)) {
            response.statusCode = httpVersionNotSupported
            return
        }

        if (request.method == Method.UNKNOWN) {
            response.statusCode = methodNotAllowed
            return
        }

        if (request.path.contains("..")) {
            response.statusCode = forbidden
            return
        }

        val filePath = Paths.get(documentRoot.toString(), request.path)
        if (!filePath.exists()) {
            response.statusCode = notFound
            return
        }

        if (!filePath.readable()) {
            response.statusCode = forbidden
            return
        }

        response.headers.add(filePath.contentTypeHeader)
        response.headers.add(contentLength(Files.size(filePath)))

        if (request.method == Method.GET) {
            response.body = FileInputStream(filePath.toFile())
        }
    }
}