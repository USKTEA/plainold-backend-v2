package com.usktea.plainoldv2.domain.product

import jakarta.persistence.Embeddable

@Embeddable
data class Price(
    val amount: Long
)
