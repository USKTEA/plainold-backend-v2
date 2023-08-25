package com.usktea.plainoldv2.domain.token.application

import com.usktea.plainoldv2.createRefreshToken
import com.usktea.plainoldv2.createUsername
import com.usktea.plainoldv2.domain.token.RefreshToken
import com.usktea.plainoldv2.domain.token.repository.RefreshTokenRepository
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.utils.JwtUtil
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import java.util.UUID

const val USERNAME = "tjrxo1234@gmail.com"

@ActiveProfiles("test")
class TokenServiceTest {
    private val refreshTokenRepository = mockk<RefreshTokenRepository>()
    private val jwtUtil = JwtUtil("SECRET")
    private val tokenService = TokenService(refreshTokenRepository, jwtUtil)

    @Test
    fun `accessToken과 refreshToken을 생성한다`() = runTest {
        val username = createUsername(USERNAME)
        val refreshToken = createRefreshToken(USERNAME)

        coEvery { refreshTokenRepository.save(any()) } returns refreshToken

        val tokenDto = tokenService.issueToken(username)

        coVerify(exactly = 1) { refreshTokenRepository.save(any()) }
        jwtUtil.decode(tokenDto.accessToken) shouldBe USERNAME
        tokenDto.refreshToken shouldNotBe null
    }

    @Test
    fun `refreshToken이 유효하다면 토큰을 재발급한다`() = runTest {
        val refreshTokenNumber = jwtUtil.encode(UUID.randomUUID())
        val refreshToken = createRefreshToken(USERNAME)

        coEvery { refreshTokenRepository.findByNumberOrNull(refreshTokenNumber) } returns refreshToken
        coEvery { refreshTokenRepository.update(refreshToken) } just Runs

        val tokenDto = tokenService.reissueToken(refreshTokenNumber)

        coVerify(exactly = 1) { refreshTokenRepository.update(refreshToken) }
        jwtUtil.decode(tokenDto.accessToken) shouldBe USERNAME
        tokenDto.refreshToken shouldNotBe null
    }
}
