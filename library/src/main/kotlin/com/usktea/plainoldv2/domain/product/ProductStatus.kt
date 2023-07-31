package com.usktea.plainoldv2.domain.product

import jakarta.persistence.Embeddable

enum class ProductStatus(
    val status: String
) {
    ON_SALE("ON_SALE"), SOLD_OUT("SOLD_OUT")
}
