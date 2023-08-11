package com.usktea.plainoldv2.domain.order

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class PriceTest {
    @Test
    fun creation() {
        shouldNotThrowAny { Price(0L) }
        shouldThrow<IllegalArgumentException> { Price(-1L) }
    }
}
