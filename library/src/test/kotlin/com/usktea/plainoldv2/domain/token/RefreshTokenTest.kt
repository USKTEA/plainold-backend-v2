package com.usktea.plainoldv2.domain.token

import com.ninjasquad.springmockk.MockkBean
import com.usktea.plainoldv2.createRefreshToken
import com.usktea.plainoldv2.domain.token.repository.RefreshTokenRepository
import com.usktea.plainoldv2.utils.JwtUtil
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ActiveProfiles
import java.util.*

const val USERNAME = "tjrxo1234@gmail.com"
const val ACCESS_TOKEN = "VALID"
const val REFRESH_TOKEN = "VALID"
const val SECRET = "secret"

@ActiveProfiles("test")
class RefreshTokenTest {
    private val jwtUtil = mockk<JwtUtil>()

    @Test
    fun `새로운 accessToken과 refreshToken을 생성한다`() {
        val refreshToken = createRefreshToken(USERNAME)

        val origin = refreshToken.number

        every { jwtUtil.encode(USERNAME) } returns ACCESS_TOKEN
        every { jwtUtil.encode(any<UUID>()) } returns REFRESH_TOKEN

        val (accessToken, refreshTokenNumber) = refreshToken.toNextVersion(jwtUtil)

        val current = refreshToken.number

        accessToken shouldBe ACCESS_TOKEN
        refreshTokenNumber shouldBe REFRESH_TOKEN
        origin shouldNotBe current
        current shouldBe REFRESH_TOKEN
    }
}

