package com.usktea.plainoldv2.application.endpoints.oAuth

import com.usktea.plainoldv2.domain.oAuth.OAuthLoginRequestDto
import com.usktea.plainoldv2.domain.oAuth.RedirectUrlDto
import com.usktea.plainoldv2.domain.oAuth.application.OAuthServiceFactory
import com.usktea.plainoldv2.domain.user.LoginResultDto
import com.usktea.plainoldv2.exception.OAuthProviderNotFoundException
import com.usktea.plainoldv2.exception.RequestBodyNotFoundException
import kotlinx.coroutines.*
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class OAuthHandler(
    private val oAuthServiceFactory: OAuthServiceFactory
) {

    suspend fun getRedirectUrl(request: ServerRequest): ServerResponse {
        val provider = request.pathVariable("provider")
        val oAuthService = oAuthServiceFactory[provider] ?: throw OAuthProviderNotFoundException()
        val redirectUrl = oAuthService.getRedirectUrl()

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(RedirectUrlDto(redirectUrl))
    }

    suspend fun login(request: ServerRequest): ServerResponse {
        val loginRequestDto = request.awaitBodyOrNull<OAuthLoginRequestDto>() ?: throw RequestBodyNotFoundException()
        val oAuthService = oAuthServiceFactory[loginRequestDto.provider] ?: throw OAuthProviderNotFoundException()

        val tokenDtoDeferred = CoroutineScope(Dispatchers.IO).async {
            oAuthService.login(loginRequestDto.code)
        }

        val tokenDto = tokenDtoDeferred.await()

        val cookie = ResponseCookie.from("refreshToken", tokenDto.refreshToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .domain("localhost")
            .build()

        return ok().contentType(MediaType.APPLICATION_JSON)
            .cookie(cookie)
            .bodyValueAndAwait(LoginResultDto(tokenDto.accessToken))
    }
}
