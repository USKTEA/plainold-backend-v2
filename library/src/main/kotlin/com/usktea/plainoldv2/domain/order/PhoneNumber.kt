package com.usktea.plainoldv2.domain.order

import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    val value: String
) {
}
