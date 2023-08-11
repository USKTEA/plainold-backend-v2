package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidEmailException
import com.usktea.plainoldv2.utils.RegExpProvider
import jakarta.persistence.Embeddable

@Embeddable
data class Email(
    val value: String
) {
    init {
        if (!value.matches(RegExpProvider.email())) {
            throw InvalidEmailException()
        }
    }
}
