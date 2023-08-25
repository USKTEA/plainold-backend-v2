package com.usktea.plainoldv2.domain.review

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class OrderNumber(
    @Column(name = "orderNumber")
    val value: String
)
