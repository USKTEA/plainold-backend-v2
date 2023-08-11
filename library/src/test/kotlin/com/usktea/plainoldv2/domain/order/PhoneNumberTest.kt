package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidPhoneNumberException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class PhoneNumberTest {
    @Test
    fun creation() {
        shouldNotThrowAny { PhoneNumber("010-1111-1111") }
        shouldThrow<InvalidPhoneNumberException> { PhoneNumber("something") }
    }
}
