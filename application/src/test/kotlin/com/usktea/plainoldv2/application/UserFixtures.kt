package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.user.LoginRequestDto

const val USERNAME = "tjrxo1234@gmail.com"
const val PASSWORD = "Password1234!"
const val INVALID_PASSWORD = "InvalidPassword1!"
const val VALID_ACCESS_TOKEN = "VALID"
const val VALID_REFRESH_TOKEN = "VALID"

fun createLoginRequestDto(
    username: String = USERNAME,
    password: String = PASSWORD
): LoginRequestDto {
    return LoginRequestDto(username, password)
}

fun createTokenDto(
    accessToken: String = VALID_ACCESS_TOKEN,
    refreshToken: String = VALID_REFRESH_TOKEN
): TokenDto {
    return TokenDto(accessToken, refreshToken)
}
