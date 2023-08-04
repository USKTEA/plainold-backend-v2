package com.usktea.plainoldv2.application.endpoints.user

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createUserInformationDto
import com.usktea.plainoldv2.domain.user.application.UserService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

const val USERNAME = "tjrxo1234@gmail.com"

@ActiveProfiles("test")
@WebFluxTest(UserRouter::class, UserHandler::class)
class UserHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var userService: UserService

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @Test
    fun `Username으로 유저를 조회한다`() {
        val token = jwtUtil.encode(USERNAME)
        val userInformation = createUserInformationDto(
            username = USERNAME
        )

        coEvery { userService.getUserInformation(any()) } returns userInformation

        client.mutateWith(mockUser()).get()
            .uri("/users/me")
            .header("Authorization", "Bearer $token")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.username").isEqualTo(USERNAME)
    }
}
