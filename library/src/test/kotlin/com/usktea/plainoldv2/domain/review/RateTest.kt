package com.usktea.plainoldv2.domain.review

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class RateTest {

    @Test
    fun `Rate의 값은 1이상 5이하인 같이다`() {
        shouldThrowAny { Rate(0) }
        shouldThrowAny { Rate(6) }
        shouldNotThrowAny { Rate(1) }
        shouldNotThrowAny { Rate(5) }
    }
}
