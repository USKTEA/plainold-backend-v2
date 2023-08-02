package com.usktea.plainoldv2.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class GlobalException(errorMessage: String) : ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage)

class ProductNotFound(errorMessage: String) : GlobalException(errorMessage)
