package com.usktea.plainoldv2.domain.user

import com.usktea.plainoldv2.exception.InvalidUsernameException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UsernameTest {
    @Test
    fun creation() {
        shouldNotThrowAny { Username("tjrxo1234@gmail.com") }
        shouldThrow<InvalidUsernameException> { Username("something") }
    }

    @Test
    fun afterAt() {
        val username = Username("tjrxo1234@gmail.com")

        val afterAt = username.afterAt()

        afterAt shouldBe "tjrxo1234"
    }
}
