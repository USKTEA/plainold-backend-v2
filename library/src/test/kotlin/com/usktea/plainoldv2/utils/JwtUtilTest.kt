package com.usktea.plainoldv2.utils

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.Test
import java.util.*

class JwtUtilTest {
    private val jwtUtil = JwtUtil("SECRET")
    private val username = "tjrxo1234@gmail.com"

    @Test
    fun `특정 값을 JWT로 인코딩을 할 수 있고, JWT를 특정 값으로 다시 디코딩할 수 있다`() {
        val token = jwtUtil.encode(username)

        token shouldContain "."
        jwtUtil.decode(token) shouldBe username
    }

    @Test
    fun `refreshToken이 유효한 값인지 확인한다`() {
        val uuid = UUID.randomUUID()
        val refreshToken = jwtUtil.encode(uuid)

        shouldNotThrowAny { jwtUtil.decodeRefreshToken(refreshToken) }
    }
}
