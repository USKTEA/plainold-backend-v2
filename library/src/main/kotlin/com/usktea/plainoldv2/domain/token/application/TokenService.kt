package com.usktea.plainoldv2.domain.token.application

import com.usktea.plainoldv2.domain.token.RefreshToken
import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.token.repository.RefreshTokenRepository
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.utils.JwtUtil
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtil: JwtUtil
) : IssueTokenUseCase {
    override suspend fun issueToken(username: Username): TokenDto {
        val accessToken = jwtUtil.encode(username.value)
        val refreshToken = jwtUtil.encode(UUID.randomUUID())

        refreshTokenRepository.save(RefreshToken(username = username, number = refreshToken))

        return TokenDto(accessToken = accessToken, refreshToken = refreshToken)
    }
}
