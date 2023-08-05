package com.usktea.plainoldv2.config

import com.usktea.plainoldv2.domain.oAuth.application.KakaoOAuthService
import com.usktea.plainoldv2.domain.oAuth.application.NaverOAuthService
import com.usktea.plainoldv2.domain.oAuth.application.OAuthService
import com.usktea.plainoldv2.domain.oAuth.application.OAuthServiceFactory
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.properties.KakaoOAuthProperties
import com.usktea.plainoldv2.properties.NaverOAuthProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EnableConfigurationProperties(KakaoOAuthProperties::class, NaverOAuthProperties::class)
class OAuthConfiguration {
    @Autowired
    private lateinit var tokenService: TokenService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var kakaoOAuthProperties: KakaoOAuthProperties

    @Autowired
    private lateinit var naverOAuthProperties: NaverOAuthProperties

    @Autowired
    private lateinit var webClient: WebClient

    @Autowired
    private lateinit var passwordEncoder: Argon2PasswordEncoder

    @Bean
    fun oAuthServiceFactory(): OAuthServiceFactory {
        val services: MutableMap<String, OAuthService> = mutableMapOf()

        services["kakao"] =
            KakaoOAuthService(tokenService, userRepository, kakaoOAuthProperties, webClient, passwordEncoder)
        services["naver"] =
            NaverOAuthService(tokenService, userRepository, naverOAuthProperties, webClient, passwordEncoder)

        return OAuthServiceFactory(services)
    }
}
