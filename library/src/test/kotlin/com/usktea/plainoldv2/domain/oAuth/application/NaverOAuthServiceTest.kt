package com.usktea.plainoldv2.domain.oAuth.application

import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.properties.NaverOAuthProperties
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.web.reactive.function.client.WebClient

const val STATE = "naver"

class NaverOAuthServiceTest {
    private val tokenService = mockk<TokenService>()
    private val userRepository = mockk<UserRepository>()
    private val naverOAuthProperties = mockk<NaverOAuthProperties>()
    private val passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    private val webClient = mockk<WebClient>()

    private val naverOAuthService =
        NaverOAuthService(tokenService, userRepository, naverOAuthProperties, webClient, passwordEncoder)

    @Test
    fun `RedirectUrl을 반환한다`() {
        every { naverOAuthProperties.authorizationUri } returns AUTHORIZATION_URL
        every { naverOAuthProperties.redirectUri } returns REDIRECT_URL
        every { naverOAuthProperties.clientId } returns CLIENT_ID
        every { naverOAuthProperties.responseType } returns RESPONSE_TYPE
        every { naverOAuthProperties.state } returns STATE

        val redirectUrl = naverOAuthService.getRedirectUrl()

        redirectUrl shouldNotBe null

        verify(exactly = 1) { naverOAuthProperties.authorizationUri }
        verify(exactly = 1) { naverOAuthProperties.redirectUri }
        verify(exactly = 1) { naverOAuthProperties.clientId }
        verify(exactly = 1) { naverOAuthProperties.responseType }
        verify(exactly = 1) { naverOAuthProperties.state }
    }
}
