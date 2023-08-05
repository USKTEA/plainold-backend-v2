package com.usktea.plainoldv2.domain.oAuth.application

import com.usktea.plainoldv2.domain.token.TokenDto

interface OAuthService {
    fun getRedirectUrl(): String
    suspend fun login(code: String): TokenDto
}
