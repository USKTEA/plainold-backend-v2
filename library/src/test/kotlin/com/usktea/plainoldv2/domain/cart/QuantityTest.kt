package com.usktea.plainoldv2.domain.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class QuantityTest {
    @Test
    fun add() {
        val one = Quantity(1L)
        val two = Quantity(2L)
        val three = Quantity(3L)

        one.add(two) shouldBe three
    }
}
