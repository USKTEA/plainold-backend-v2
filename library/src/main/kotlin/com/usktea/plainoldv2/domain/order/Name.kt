package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidNamingException
import com.usktea.plainoldv2.utils.RegExpProvider
import jakarta.persistence.Embeddable

@Embeddable
data class Name(
    val value: String
) {
    init {
        if (!value.matches(RegExpProvider.name())) {
            throw InvalidNamingException()
        }
    }
}
