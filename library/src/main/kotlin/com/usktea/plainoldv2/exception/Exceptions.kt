package com.usktea.plainoldv2.exception

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class GlobalException(httpStatus: HttpStatus = HttpStatus.BAD_REQUEST, errorMessage: String) :
    ResponseStatusException(httpStatus, errorMessage)

class ProductNotFoundException : GlobalException(errorMessage = ErrorMessage.PRODUCT_NOT_FOUND.value)
class LoginFailedException : GlobalException(HttpStatus.FORBIDDEN, ErrorMessage.LOGIN_FAILED.value)
class UnIdentifiedUserException : GlobalException(errorMessage = ErrorMessage.UNIDENTIFIED_USER.value)
class RequestAttributeNotFoundException : GlobalException(errorMessage = ErrorMessage.REQUEST_ATTRIBUTE_NOT_FOUND.value)
class ParameterNotFoundException : GlobalException(errorMessage = ErrorMessage.PARAMETER_NOT_FOUND.value)
class RequestBodyNotFoundException : GlobalException(errorMessage = ErrorMessage.REQUEST_BODY_NOT_FOUND.value)
class UsernameAlreadyInUse : GlobalException(errorMessage = ErrorMessage.USERNAME_ALREADY_IN_USE.value)
class OAuthProviderNotFoundException : GlobalException(errorMessage = ErrorMessage.OAUTH_PROVIDER_NOT_FOUND.value)
class InvalidEmailException : GlobalException(errorMessage = ErrorMessage.INVALID_EMAIL.value)
class InvalidUsernameException : GlobalException(errorMessage = ErrorMessage.INVALID_USERNAME.value)
class InvalidNamingException : GlobalException(errorMessage = ErrorMessage.INVALID_NAME.value)
class InvalidPhoneNumberException : GlobalException(errorMessage = ErrorMessage.INVALID_PHONENUMBER.value)
class InvalidZipCodeException : GlobalException(errorMessage = ErrorMessage.INVALID_ZIPCODE.value)
class InvalidAddressException : GlobalException(errorMessage = ErrorMessage.INVALID_ADDRESS.value)
class UserNotExistsException : GlobalException(errorMessage = ErrorMessage.USER_NOT_EXISTS.value)
class InvalidOrderItemException : GlobalException(errorMessage = ErrorMessage.INVALID_ORDER_ITEM.value)
class PaymentProviderNotFoundException : GlobalException(errorMessage = ErrorMessage.PAYMENT_PROVIDER_NOT_FOUND.value)
class PrePaymentNotExistsException : GlobalException(errorMessage = ErrorMessage.PREPAYMENT_NOT_EXISTS.value)
class KakaopayReadyException : GlobalException(errorMessage = ErrorMessage.KAKAOPAY_READY.value)
class KakaopayApproveException : GlobalException(errorMessage = ErrorMessage.KAKAOPAY_APPROVE.value)
class CartItemNotFoundException : GlobalException(errorMessage = ErrorMessage.CART_ITEM_NOT_FOUND.value)
class CartNotFoundException : GlobalException(errorMessage = ErrorMessage.CART_NOT_FOUND.value)
class CookieNotFoundException : GlobalException(errorMessage = ErrorMessage.COOKIE_NOT_FOUND.value)
class RefreshTokenNotFoundException : GlobalException(errorMessage = ErrorMessage.REFRESH_TOKEN_NOT_FOUND.value)
class OrderCanWriteReviewNotFoundException :
    GlobalException(httpStatus = HttpStatus.NOT_FOUND ,errorMessage = ErrorMessage.ORDER_CAN_WRITE_REVIEW_NOT_FOUND.value)
class ReviewAlreadyWrittenException : GlobalException(errorMessage = ErrorMessage.REVIEW_ALREADY_WRITTEN.value)
class ReviewNotFoundException : GlobalException(errorMessage = ErrorMessage.REVIEW_NOT_FOUND.value)
