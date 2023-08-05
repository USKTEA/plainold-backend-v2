package com.usktea.plainoldv2.domain.oAuth.application

import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.properties.KakaoOAuthProperties
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import org.springframework.web.reactive.function.client.WebClient

const val REDIRECT_URL = "kakao"
const val AUTHORIZATION_URL = "kakao"
const val CLIENT_ID = "kakao"
const val RESPONSE_TYPE = "kakao"

@ActiveProfiles("test")
@EnableConfigurationProperties(KakaoOAuthProperties::class)
class KakaoOAuthServiceTest {
    private val tokenService = mockk<TokenService>()
    private val userRepository = mockk<UserRepository>()
    private val kakaoOAuthProperties = mockk<KakaoOAuthProperties>()
    private val passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    private val webClient = mockk<WebClient>()

    private val kakaoOAuthService =
        KakaoOAuthService(tokenService, userRepository, kakaoOAuthProperties, webClient, passwordEncoder)

    @Test
    fun `RedriectUrl을 반환한다`() {
        every { kakaoOAuthProperties.authorizationUri } returns AUTHORIZATION_URL
        every { kakaoOAuthProperties.redirectUri } returns REDIRECT_URL
        every { kakaoOAuthProperties.clientId } returns CLIENT_ID
        every { kakaoOAuthProperties.responseType } returns RESPONSE_TYPE

        val redirectUrl = kakaoOAuthService.getRedirectUrl()

        redirectUrl shouldNotBe null

        verify(exactly = 1) { kakaoOAuthProperties.authorizationUri }
        verify(exactly = 1) { kakaoOAuthProperties.redirectUri }
        verify(exactly = 1) { kakaoOAuthProperties.clientId }
        verify(exactly = 1) { kakaoOAuthProperties.responseType }
    }
}
