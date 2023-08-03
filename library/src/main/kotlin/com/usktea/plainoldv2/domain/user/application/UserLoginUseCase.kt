package com.usktea.plainoldv2.domain.user.application

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.user.LoginRequest

interface UserLoginUseCase {
    suspend fun login(loginRequest: LoginRequest): TokenDto
}
