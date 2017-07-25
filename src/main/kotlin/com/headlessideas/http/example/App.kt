package com.headlessideas.http.example

import com.headlessideas.http.Server
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import kotlin.system.exitProcess


fun main(args: Array<String>): Unit = mainBody("klutch") {
    Args(ArgParser(args)).run {

        val message = checkConfig(configFile)

        if (message.isNotEmpty()) {
            println(message)
            if (check) {
                exitProcess(0)
            }
            exitProcess(1)
        }

        if (!check) {
            val root = configFile.getProperty("server.root").toPath()
            val port = configFile.getProperty("server.port").toInt()

            val server = Server(root, port)
            server.serve()
        } else {
            println("OK")
        }
    }

}

fun checkConfig(prop: Properties): String {
    var message = ""

    if (prop.getProperty("server.root") == null) {
        message += "server.root is missing\r\n"
    }
    if (prop.getProperty("server.port") == null) {
        message += "server.port is missing\r\n"
    }

    return message
}

fun String.toPath(): Path = Paths.get(this)

class Args(parser: ArgParser) {
    val check by parser.flagging("-c", "--check", help = "Checks the provided configuration file")
    val configFile by parser.positional("CONFIG", help = "The configuration file for the web server") {
        val prop = Properties()
        prop.load(toPath().toFile().inputStream())
        prop
    }
}