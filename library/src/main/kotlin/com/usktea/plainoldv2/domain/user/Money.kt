package com.usktea.plainoldv2.domain.user

import jakarta.persistence.Embeddable

@Embeddable
data class Money(
    val amount: Long
)
