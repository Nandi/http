package com.headlessideas.http.example

import com.headlessideas.http.Server
import java.nio.file.Path
import java.nio.file.Paths

fun main(args: Array<String>) {
    val server = Server("/".toPath(), 9000)

    server.serve()
}

fun String.toPath(): Path = Paths.get(this)