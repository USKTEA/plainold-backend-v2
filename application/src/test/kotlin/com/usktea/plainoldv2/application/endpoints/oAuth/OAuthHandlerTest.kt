package com.usktea.plainoldv2.application.endpoints.oAuth

import com.ninjasquad.springmockk.MockkBean
import com.usktea.plainoldv2.application.createOAuthLoginRequestDto
import com.usktea.plainoldv2.application.createTokenDto
import com.usktea.plainoldv2.domain.oAuth.application.KakaoOAuthService
import com.usktea.plainoldv2.domain.oAuth.application.OAuthServiceFactory
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient


const val PROVIDER = "kakao"
const val REDIRECT_URL = "REDIRECT_URL"
const val CODE = "SOME_CODE"

@ActiveProfiles("test")
@WebFluxTest(OAuthRouter::class, OAuthHandler::class)
class OAuthHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var oAuthServiceFactory: OAuthServiceFactory

    @MockkBean
    private lateinit var kakaoOAuthService: KakaoOAuthService

    @MockkBean
    private lateinit var jwtUtil: JwtUtil

    @Test
    fun `RedirectUrl을 조회한다`() {
        coEvery { oAuthServiceFactory[PROVIDER] } returns kakaoOAuthService
        coEvery { kakaoOAuthService.getRedirectUrl() } returns REDIRECT_URL

        client.mutateWith(mockUser())
            .get()
            .uri("/oauth/$PROVIDER")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.redirectUrl").exists()
    }

    @Test
    fun `올바른 로그인 요청에 Token을 반환한다`() {
        val oAuthLoginRequestDto = createOAuthLoginRequestDto(
            provider = PROVIDER,
            code = CODE
        )
        val tokenDto = createTokenDto()

        coEvery { oAuthServiceFactory[PROVIDER] } returns kakaoOAuthService
        coEvery { kakaoOAuthService.login(CODE) } returns tokenDto

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/oauth/session")
            .bodyValue(oAuthLoginRequestDto)
            .exchange()
            .expectStatus().isOk
            .expectCookie().exists("refreshToken")
            .expectBody()
            .jsonPath("$.accessToken").exists()
    }
}
