package com.headlessideas.http

import com.headlessideas.http.util.html
import com.headlessideas.http.util.ok
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.File
import java.io.InputStreamReader
import java.net.Socket


/**
 * todo: implement coroutines
 */

suspend fun handleClient(client: Socket) {
    val input = BufferedReader(InputStreamReader(client.getInputStream()))
    val output = DataOutputStream(client.getOutputStream())

    val requestLine = input.readLine()
    val headers = mutableListOf<Header>()

    // read and print out the rest of the request
    var s = input.readLine()
    while (s != null && s.isNotEmpty()) {
        val (name, value) = s.split(": ", limit = 2)
        headers.add(Header(name, value))
        s = input.readLine()
    }

    val (method, path) = requestLine.split(" ")
    val request = Request(Method.valueOf(method), path, headers)
    println(request)

    val response = Response(ok, listOf(html), File("./"))
    println(response)

    output.writeBytes(response.toString())
    //output.write(getFile("/").readBytes())
    output.write("".toByteArray(), 0, 0)

    output.close()
    client.close()
}