package com.usktea.plainoldv2.application.endpoints.token

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createTokenDto
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@ActiveProfiles("test")
@WebFluxTest(TokenRouter::class, TokenHandler::class)
class TokenHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @MockkBean
    private lateinit var tokenService: TokenService

    @Test
    fun `사용자는 RefreshToken을 이용해 AccesToken을 재발급 할 수 있다`() {
        val refreshToken = jwtUtil.encode(UUID.randomUUID())
        val tokenDto = createTokenDto()

        coEvery { tokenService.reissueToken(refreshToken) } returns tokenDto

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/tokens")
            .cookie("refreshToken", refreshToken)
            .exchange()
            .expectStatus().isOk
            .expectCookie().exists("refreshToken")
            .expectBody()
            .jsonPath("$.accessToken").exists()
    }
}
