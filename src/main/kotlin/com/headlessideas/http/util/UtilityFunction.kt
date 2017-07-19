package com.headlessideas.http.util

import java.io.File

fun getContentType(file: File) = when (file.extension) {
    "html", "htm" -> html
    "css" -> css
    "csv" -> csv
    "png" -> png
    "jpg", "jpeg" -> jpeg
    "gif" -> gif
    "js" -> js
    "json" -> json
    else -> plain
}