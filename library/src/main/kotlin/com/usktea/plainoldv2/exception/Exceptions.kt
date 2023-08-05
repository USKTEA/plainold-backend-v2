package com.usktea.plainoldv2.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class GlobalException(httpStatus: HttpStatus = HttpStatus.BAD_REQUEST, errorMessage: String) :
    ResponseStatusException(httpStatus, errorMessage)

class ProductNotFoundException : GlobalException(errorMessage = PRODUCT_NOT_FOUND)
class LoginFailedException : GlobalException(HttpStatus.FORBIDDEN, LOGIN_FAILED)
class UnIdentifiedUserException : GlobalException(errorMessage = UNIDENTIFIED_USER)
class RequestAttributeNotFoundException : GlobalException(errorMessage = REQUEST_ATTRIBUTE_NOT_FOUND)
class ParameterNotFoundException : GlobalException(errorMessage = PARAMETER_NOT_FOUND)
class RequestBodyNotFoundException : GlobalException(errorMessage = REQUEST_BODY_NOT_FOUND)
class UsernameAlreadyInUse : GlobalException(errorMessage = USERNAME_ALREADY_IN_USE)
