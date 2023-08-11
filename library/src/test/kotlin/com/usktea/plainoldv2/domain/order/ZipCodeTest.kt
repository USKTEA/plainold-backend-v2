package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidZipCodeException
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import org.junit.jupiter.api.Test

class ZipCodeTest {
    @Test
    fun creation() {
        shouldNotThrowAny { ZipCode("111111")  }
        shouldThrow<InvalidZipCodeException> { ZipCode("글자")  }
        shouldThrow<InvalidZipCodeException> { ZipCode("11111")  }
        shouldThrow<InvalidZipCodeException> { ZipCode("1111111")  }
    }
}
