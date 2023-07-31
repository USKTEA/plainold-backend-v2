package com.usktea.plainoldv2.domain.product

import jakarta.persistence.Embeddable

@Embeddable
data class ProductName(
    val value: String
)
