package com.usktea.plainoldv2.domain.order

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import org.junit.jupiter.api.Test

class QuantityTest {
    @Test
    fun creation() {
        shouldNotThrowAny { Quantity(1L) }
        shouldThrowAny { Quantity(0L) }
        shouldThrowAny { Quantity(-1L) }
    }
}
