package com.headlessideas.http.util

import com.headlessideas.http.StatusCode

val notFound = StatusCode(404, "Not Found")
val ok = StatusCode(200, "OK")
val forbidden = StatusCode(403, "Forbidden")
val internalServerError = StatusCode(500, "Internal Server Error")