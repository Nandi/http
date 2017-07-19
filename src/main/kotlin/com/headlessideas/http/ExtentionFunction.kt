package com.headlessideas.http

import java.io.File
import java.nio.file.Paths

fun getFile(path: String): File = Paths.get(path).toFile()