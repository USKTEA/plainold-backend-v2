package com.usktea.plainoldv2.domain.user

import jakarta.persistence.Embeddable

data class LoginRequestDto(
    val username: String,
    val password: String,
) {
    fun toVo(): LoginRequest {
        return LoginRequest(Username(username), Password(password))
    }
}

data class LoginRequest(
    val username: Username,
    val password: Password
)

data class LoginResultDto(
    val accessToken: String
)
