package com.usktea.plainoldv2.application.endpoints.token

import com.usktea.plainoldv2.domain.token.ReissueTokenResultDto
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.exception.CookieNotFoundException
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class TokenHandler(
    private val tokenService: TokenService
) {

    suspend fun reissueToken(request: ServerRequest): ServerResponse {
        val refreshToken = request.cookies().getFirst("refreshToken")?.value ?: throw CookieNotFoundException()

        println("--")
        println(refreshToken)
        println("--")
        val tokenDto = tokenService.reissueToken(refreshToken)
        val cookie = ResponseCookie.from("refreshToken", tokenDto.refreshToken)
            .httpOnly(true)
            .path("/")
            .sameSite("Lax")
            .domain("localhost")
            .build()

        return ok().contentType(MediaType.APPLICATION_JSON)
            .cookie(cookie)
            .bodyValueAndAwait(ReissueTokenResultDto(tokenDto.accessToken))
    }
}
