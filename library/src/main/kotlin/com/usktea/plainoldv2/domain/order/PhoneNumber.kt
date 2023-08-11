package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.exception.InvalidPhoneNumberException
import com.usktea.plainoldv2.utils.RegExpProvider
import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    val value: String
) {
    init {
        if (!value.matches(RegExpProvider.phoneNumber())) {
            throw InvalidPhoneNumberException()
        }
    }
}
