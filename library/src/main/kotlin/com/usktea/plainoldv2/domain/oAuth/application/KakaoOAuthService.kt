package com.usktea.plainoldv2.domain.oAuth.application

import com.usktea.plainoldv2.domain.oAuth.KakaoTokenResponse
import com.usktea.plainoldv2.domain.oAuth.OauthAttributes
import com.usktea.plainoldv2.domain.oAuth.UserProfile
import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.properties.KakaoOAuthProperties
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class KakaoOAuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val properties: KakaoOAuthProperties,
    private val webClient: WebClient,
    private val passwordEncoder: PasswordEncoder
) : OAuthService {
    override fun getRedirectUrl(): String {
        return "${properties.authorizationUri}" +
                "?client_id=${properties.clientId}" +
                "&redirect_uri=${properties.redirectUri}" +
                "&response_type=${properties.responseType}"
    }

    override suspend fun login(code: String): TokenDto {
        val tokenResponse = getToken(code)
        val userProfile = getUserProfile(tokenResponse)

        val user = userRepository.findByUsernameOrNull(Username(userProfile.email))

        if (user == null) {
            return User.createRoleMember(userProfile, passwordEncoder)
                .also { userRepository.save(it) }
                .let { tokenService.issueToken(it.username) }
        }

        return tokenService.issueToken(user.username)
    }

    private suspend fun getUserProfile(tokenResponse: KakaoTokenResponse): UserProfile {
        val userAttribute = getUserAttribute(tokenResponse)

        return OauthAttributes.extract(properties.provider, userAttribute)
    }

    private suspend fun getUserAttribute(tokenResponse: KakaoTokenResponse): Map<String, Any> {
        return webClient.get()
            .uri(properties.userInformationUri)
            .header("Authorization", "Bearer " + tokenResponse.access_token)
            .retrieve()
            .awaitBody<Map<String, Any>>()
    }

    private suspend fun getToken(code: String): KakaoTokenResponse {
        return webClient.post()
            .uri(properties.tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(tokenRequest(code))
            .retrieve()
            .awaitBody<KakaoTokenResponse>()
    }

    private fun tokenRequest(code: String): MultiValueMap<String, String> {
        val formData = LinkedMultiValueMap<String, String>()

        formData["grant_type"] = properties.grantType
        formData["client_id"] = properties.clientId
        formData["redirect_uri"] = properties.redirectUri
        formData["code"] = code
        formData["client_secret"] = properties.clientSecret

        return formData
    }
}
