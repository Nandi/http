package com.headlessideas.http

import java.nio.file.Path
import java.nio.file.Paths

fun main(args: Array<String>) {
    val server = Server("/".toPath(), 8000)

    server.serve()
}

fun String.toPath(): Path = Paths.get(this)