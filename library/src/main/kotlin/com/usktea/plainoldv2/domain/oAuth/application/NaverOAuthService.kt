package com.usktea.plainoldv2.domain.oAuth.application

import com.usktea.plainoldv2.domain.oAuth.NaverTokenResponse
import com.usktea.plainoldv2.domain.oAuth.OauthAttributes
import com.usktea.plainoldv2.domain.oAuth.UserProfile
import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.properties.NaverOAuthProperties
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Service
class NaverOAuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val properties: NaverOAuthProperties,
    private val webClient: WebClient,
    private val passwordEncoder: PasswordEncoder
) : OAuthService {
    override fun getRedirectUrl(): String {
        return "${properties.authorizationUri}" +
                "?response_type=${properties.responseType}" +
                "&client_id=${properties.clientId}" +
                "&state=${properties.state}" +
                "&redirect_uri=${properties.redirectUri}"
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

    private suspend fun getUserProfile(tokenResponse: NaverTokenResponse): UserProfile {
        val userAttribute = getUserAttribute(tokenResponse)

        return OauthAttributes.extract(properties.provider, userAttribute)
    }

    private suspend fun getUserAttribute(tokenResponse: NaverTokenResponse): Map<String, Any> {
        return webClient.get()
            .uri(properties.userInformationUri)
            .header("Authorization", "Bearer " + tokenResponse.access_token)
            .retrieve()
            .awaitBody<Map<String, Any>>()
    }

    private suspend fun getToken(code: String): NaverTokenResponse {
        return webClient.post()
            .uri(properties.tokenUri)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(tokenRequest(code))
            .retrieve()
            .awaitBody<NaverTokenResponse>()
    }

    private fun tokenRequest(code: String): MultiValueMap<String, String> {
        val formData = LinkedMultiValueMap<String, String>()

        formData["grant_type"] = properties.grantType
        formData["client_id"] = properties.clientId
        formData["client_secret"] = properties.clientSecret
        formData["code"] = code
        formData["state"] = properties.state

        return formData
    }
}
