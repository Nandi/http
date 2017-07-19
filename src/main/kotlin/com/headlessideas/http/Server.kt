package com.headlessideas.http

import java.net.ServerSocket
import java.nio.file.Path

class Server(val documentRoot: Path, val port: Int) {

    fun serve(): Nothing {

        val socket = ServerSocket(port)
        println("Listening on port $port")

        while (true) {
            val client = socket.accept()
            println("client connected")
            handleClient(client)
        }
    }
}