package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.user.LoginRequestDto
import com.usktea.plainoldv2.domain.user.UserInformationDto

const val USERNAME = "tjrxo1234@gmail.com"
const val PASSWORD = "Password1234!"
const val INVALID_PASSWORD = "InvalidPassword1!"
const val VALID_ACCESS_TOKEN = "VALID"
const val VALID_REFRESH_TOKEN = "VALID"
const val NICKNAME = "누구인가"
const val ROLE = "ROLE_MEMBER"
const val PURCHASE_AMOUNT = 0L

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

fun createUserInformationDto(username: String): UserInformationDto {
    return UserInformationDto(
        username = username,
        nickname = NICKNAME,
        purchaseAmount = PURCHASE_AMOUNT,
        role = ROLE,
    )
}
