package com.usktea.plainoldv2.domain.token.application

import com.usktea.plainoldv2.domain.token.TokenDto

interface ReissueTokenUseCase {
    suspend fun reissueToken(refreshToken: String): TokenDto
}
