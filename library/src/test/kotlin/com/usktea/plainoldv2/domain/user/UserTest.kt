package com.usktea.plainoldv2.domain.user

import com.usktea.plainoldv2.createUser
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.test.context.ActiveProfiles

const val USERNAME = "tjrxo1234@gmail.com"
const val ORIGIN_PASSWORD = "Password1234!"
const val OTHER_PASSWORD = "NotPassword1234!"

@ActiveProfiles("test")
class UserTest {
    private lateinit var user: User
    private val passwordEncoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()

    @BeforeEach
    fun setup() {
        val username = Username(USERNAME)
        val password = Password(ORIGIN_PASSWORD)

        user = createUser(username, password)
    }

    @Test
    fun `유저의 비밀번호를 수정한다`() {
        user.password shouldBe Password(ORIGIN_PASSWORD)

        user.changePassword(Password(OTHER_PASSWORD), passwordEncoder)

        user.password shouldNotBe Password(ORIGIN_PASSWORD)
        passwordEncoder.matches(OTHER_PASSWORD, user.password.value) shouldBe true
    }

    @Test
    fun `유저의 비밀번호와 일치하는지 확인한다`() {
        user.changePassword(Password(ORIGIN_PASSWORD), passwordEncoder)

        shouldNotThrowAny { user.authenticate(Password(ORIGIN_PASSWORD), passwordEncoder) }
    }
}
