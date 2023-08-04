package com.usktea.plainoldv2.application.endpoints.session

import com.usktea.plainoldv2.domain.user.LoginRequest
import com.usktea.plainoldv2.domain.user.LoginRequestDto
import com.usktea.plainoldv2.domain.user.LoginResultDto
import com.usktea.plainoldv2.domain.user.application.UserService
import org.springframework.http.MediaType
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class SessionHandler(
    private val userService: UserService
) {
    suspend fun login(request: ServerRequest): ServerResponse {
        val loginRequest = LoginRequest.from(request.awaitBody<LoginRequestDto>())
        val tokenDto = userService.login(loginRequest)
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
