package com.headlessideas.http.util

import com.headlessideas.http.StatusCode

val ok = StatusCode(200, "OK")
val forbidden = StatusCode(403, "Forbidden")
val notFound = StatusCode(404, "Not Found")
val methodNotAllowed = StatusCode(405, "Method Not Allowed")
val internalServerError = StatusCode(500, "Internal Server Error")
val httpVersionNotSupported = StatusCode(505, "HTTP Version Not Supported")