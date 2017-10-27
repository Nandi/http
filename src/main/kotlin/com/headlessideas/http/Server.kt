package com.headlessideas.http

import com.headlessideas.http.util.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import mu.KLogging
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Server(private val documentRoot: Path, private val port: Int) {

    private val httpVersions = setOf("HTTP/1.0", "HTTP/1.1")

    companion object : KLogging()

    fun serve(): Nothing {

        val socket = ServerSocket(port)
        logger.info("Listening on port $port")

        while (true) {
            val client = socket.accept()
            launch(CommonPool) {
                try {
                    client.use {
                        handleClient(client)
                    }
                } catch (e: Exception) {
                    logger.error { e }
                }
            }
        }
    }

    private suspend fun handleClient(client: Socket) {
        val input = BufferedReader(InputStreamReader(client.getInputStream()))

        val request = Request.fromStream(input)
        request.clientAddress = client.remoteSocketAddress as InetSocketAddress
        logger.debug { request }
        val response = Response()

        try {
            handleRequest(request, response)
        } catch (e: Exception) {
            response.statusCode = internalServerError
            logger.error { e }
        }

        val output = DataOutputStream(client.getOutputStream())

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

        val contentTypeHeader = filePath.contentTypeHeader
        if (contentTypeHeader == plain) {
            logger.debug { "File extension not supported: $filePath" }
        }

        response.headers.add(contentTypeHeader)
        response.headers.add(contentLength(Files.size(filePath)))

        if (request.method == Method.GET || request.method == Method.POST) {
            response.body = FileInputStream(filePath.toFile())
        }
    }
}