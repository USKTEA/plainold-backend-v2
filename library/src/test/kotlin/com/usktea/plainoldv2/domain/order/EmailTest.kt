package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidEmailException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class EmailTest {
    @Test
    fun creation() {
        shouldNotThrowAny { Email("tjrxo1234@gmail.com") }
        shouldThrow<InvalidEmailException> { Email("Something") }
    }
}
