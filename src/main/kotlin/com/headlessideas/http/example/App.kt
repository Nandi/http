package com.headlessideas.http.example

import com.headlessideas.http.Server
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import java.nio.file.Path
import java.nio.file.Paths

fun main(args: Array<String>): Unit = mainBody("klutch") {

    Args(ArgParser(args)).run {
        val server = Server(rootDir, port)
        server.serve()
    }

}

fun String.toPath(): Path = Paths.get(this)

class Args(parser: ArgParser) {
    val port by parser.storing("-p", "--port", help = "Port number, default 9000", argName = "PORT_NUMBER") { toInt() }.default(9000)
    val rootDir by parser.positional("ROOT_DIR", help = "The document root for the web server") { toPath() }
}