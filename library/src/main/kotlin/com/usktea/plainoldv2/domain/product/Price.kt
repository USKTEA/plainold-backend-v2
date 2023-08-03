package com.usktea.plainoldv2.domain.product

import com.usktea.plainoldv2.exception.PRICE_AMOUNT_EXCEPTION
import jakarta.persistence.Embeddable

@Embeddable
data class Price(
    val amount: Long
) {
    init {
        require(amount >= 0) { PRICE_AMOUNT_EXCEPTION }
    }
}
