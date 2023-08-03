package com.usktea.plainoldv2.application.endpoints.session

import com.ninjasquad.springmockk.MockkBean
import com.usktea.plainoldv2.application.INVALID_PASSWORD
import com.usktea.plainoldv2.application.createLoginRequestDto
import com.usktea.plainoldv2.application.createTokenDto
import com.usktea.plainoldv2.domain.user.application.UserService
import com.usktea.plainoldv2.exception.LoginFailedException
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(SessionRouter::class, SessionHandler::class)
class SessionHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var userService: UserService

    @Test
    fun `정상적으로 로그인이 되면 accessToken과 refreshToken을 발급한다`() {
        val loginRequestDto = createLoginRequestDto()
        val loginRequest = loginRequestDto.toVo()

        val tokenDto = createTokenDto()

        coEvery { userService.login(loginRequest) } returns tokenDto

        client.mutateWith(csrf())
            .mutateWith(mockUser()).post()
            .uri("/session")
            .bodyValue(loginRequestDto)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.accessToken").exists()
    }

    @Test
    fun `잘못된 회원 로그인 요청에 응답으로 403 Forbidden을 반환한다`() {
        val loginRequestDto = createLoginRequestDto(password = INVALID_PASSWORD)
        val loginRequest = loginRequestDto.toVo()

        coEvery { userService.login(loginRequest) } throws LoginFailedException()

        client.mutateWith(csrf())
            .mutateWith(mockUser()).post()
            .uri("/session")
            .bodyValue(loginRequestDto)
            .exchange()
            .expectStatus().isForbidden
    }
}
