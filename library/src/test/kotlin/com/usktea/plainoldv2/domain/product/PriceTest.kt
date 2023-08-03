package com.usktea.plainoldv2.domain.product

import io.kotest.assertions.throwables.shouldThrowAny
import org.junit.jupiter.api.Test

class PriceTest {

    @Test
    fun `가격은 항상 0원 이상이다`() {
        shouldThrowAny {
            Price(-1L)
        }
    }
}
