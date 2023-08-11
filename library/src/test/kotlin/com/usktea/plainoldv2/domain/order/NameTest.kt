package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidNamingException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class NameTest {
    @Test
    fun creation() {
        shouldNotThrowAny { Name("김뚜루") }
        shouldThrow<InvalidNamingException> { Name("김") }
    }
}
