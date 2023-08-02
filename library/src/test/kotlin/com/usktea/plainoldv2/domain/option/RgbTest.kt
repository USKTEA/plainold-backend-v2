package com.usktea.plainoldv2.domain.option

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import org.junit.jupiter.api.Test

class RgbTest {
    @Test
    fun `Rgb 값은 항상 0과 255 사이에 있다`() {
        shouldNotThrowAny {
            listOf(Rgb(0), Rgb(255))
        }

        shouldThrowAny {
            Rgb(-1)
        }

        shouldThrowAny {
            Rgb(256)
        }
    }
}
