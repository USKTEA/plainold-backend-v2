package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.user.LoginRequest
import com.usktea.plainoldv2.domain.user.Password
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username

fun createLoginRequest(username: Username, password: Password): LoginRequest {
    return LoginRequest(username, password)
}

fun createUsername(value: String): Username {
    return Username(value)
}

fun createPassword(value: String): Password {
    return Password(value)
}

fun createUser(username: Username, password: Password = Password("Default_Password!")): User {
    return User.fake().apply {
        this.username = username
        this.password = password
    }
}

fun createTokenDto(accessToken: String, refreshToken: String): TokenDto {
    return TokenDto(accessToken, refreshToken)
}
