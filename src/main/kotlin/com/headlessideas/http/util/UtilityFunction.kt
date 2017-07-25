package com.headlessideas.http.util

import com.headlessideas.http.Header
import java.nio.file.Files
import java.nio.file.Path

val Path.contentTypeHeader: Header
    get() = getContentType(this)

private fun getContentType(path: Path) = when (path.extension) {
    "html", "htm" -> html
    "css" -> css
    "csv" -> csv
    "png" -> png
    "jpg", "jpeg" -> jpeg
    "gif" -> gif
    "js" -> js
    "json" -> json
    "svg" -> svg
    else -> plain
}

fun Path.exists(): Boolean = Files.exists(this)
fun Path.readable(): Boolean = Files.isReadable(this)
val Path.extension: String
    get() = toFile().extension