package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.user.*
import com.usktea.plainoldv2.domain.user.application.NICKNAME

fun createLoginRequest(username: Username, password: Password): LoginRequest {
    return LoginRequest(username, password)
}

fun createUsername(value: String): Username {
    return Username(value)
}

fun createPassword(value: String): Password {
    return Password(value)
}

fun createNickname(value: String): Nickname {
    return Nickname(value)
}

fun createUser(username: Username, password: Password = Password("Default_Password!")): User {
    return User.fake().apply {
        this.username = username
        this.password = password
    }
}

fun createUserSignUpRequest(username: Username, password: Password, nickname: Nickname): SignUpRequest {
    return SignUpRequest(username, password, nickname)
}

fun createTokenDto(accessToken: String, refreshToken: String): TokenDto {
    return TokenDto(accessToken, refreshToken)
}
