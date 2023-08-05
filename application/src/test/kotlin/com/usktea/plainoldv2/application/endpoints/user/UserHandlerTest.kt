package com.usktea.plainoldv2.application.endpoints.user

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createSignUpRequestDto
import com.usktea.plainoldv2.application.createSignUpResult
import com.usktea.plainoldv2.application.createUserInformationDto
import com.usktea.plainoldv2.application.createUsername
import com.usktea.plainoldv2.domain.user.SignUpRequest
import com.usktea.plainoldv2.domain.user.application.UserService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

const val USERNAME = "tjrxo1234@gmail.com"
const val PASSWORD = "Password1234!"
const val NICKNAME = "김뚜루"

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

    @Test
    fun `특정 조건으로 유저의 수를 조회한다`() {
        val username = createUsername(USERNAME)

        coEvery { userService.count(username) } returns 1L

        client.mutateWith(mockUser()).get()
            .uri("/users?username=$USERNAME")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.count").isEqualTo(1L)
    }

    @Test
    fun `유저를 생성한다`() {
        val signupRequestDto = createSignUpRequestDto(
            username = USERNAME,
            password = PASSWORD,
            nickname = NICKNAME
        )
        val signUpRequest = SignUpRequest.from(signupRequestDto)
        val signUpResult = createSignUpResult(
            username = USERNAME
        )

        coEvery { userService.signUp(signUpRequest) } returns signUpResult

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/users")
            .bodyValue(signupRequestDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.username").isEqualTo(USERNAME)
    }
}
