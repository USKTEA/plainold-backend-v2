package com.usktea.plainoldv2.domain.user.application

import com.usktea.plainoldv2.*
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.exception.LoginFailedException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder

const val USERNAME = "tjrxo1234@gmail.com"
const val PASSWORD = "Password1234!"
const val VALID_TOKEN = "SOME_VALID_TOKEN"

class UserServiceTest {
    private val tokenService = mockk<TokenService>()
    private val userRepository = mockk<UserRepository>()
    private val passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    private val userService = UserService(tokenService, userRepository, passwordEncoder)

    @Test
    fun `특정 회원이 있는경우 로그인 정보가 정확하면 토큰을 발급한다`() = runTest {
        val username = createUsername(
            value = USERNAME
        )
        val password = createPassword(
            value = PASSWORD
        )
        val loginRequest = createLoginRequest(
            username = username,
            password = password
        )
        val user = createUser(
            username = username,
        )
        val tokenDto = createTokenDto(
            accessToken = VALID_TOKEN,
            refreshToken = VALID_TOKEN
        )

        user.changePassword(password, passwordEncoder)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { tokenService.issueToken(username) } returns tokenDto

        val actual = userService.login(loginRequest)

        actual.accessToken shouldBe VALID_TOKEN
        actual.refreshToken shouldBe VALID_TOKEN
    }

    @Test
    fun `특정 회원을 찾을 수 경우 예외가 발생한다`() = runTest {
        val username = createUsername(
            value = USERNAME
        )
        val password = createPassword(
            value = PASSWORD
        )
        val loginRequest = createLoginRequest(
            username = username,
            password = password
        )

        coEvery { userRepository.findByUsernameOrNull(username) } returns null

        shouldThrow<LoginFailedException> { userService.login(loginRequest) }
    }
}
