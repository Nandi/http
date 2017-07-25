package com.headlessideas.http.util

import com.headlessideas.http.Header

val html = Header("Content-type", "text/html")
val css = Header("Content-type", "text/css")
val csv = Header("Content-type", "text/csv")
val plain = Header("Content-type", "text/plain")
val png = Header("Content-type", "image/png")
val jpeg = Header("Content-type", "image/jpeg")
val gif = Header("Content-type", "image/gif")
val svg = Header("Content-type", "image/svg+xml")
val js = Header("Content-type", "application/javascript")
val json = Header("Content-type", "application/json")

fun contentLength(size: Long) = Header("Content-Length", "$size")
