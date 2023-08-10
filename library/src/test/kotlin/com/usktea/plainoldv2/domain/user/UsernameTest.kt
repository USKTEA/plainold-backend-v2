package com.usktea.plainoldv2.domain.user

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class UsernameTest {

    @Test
    fun afterAt() {
        val username = Username("tjrxo1234@gmail.com")

        val afterAt = username.afterAt()

        afterAt shouldBe "tjrxo1234"
    }
}
