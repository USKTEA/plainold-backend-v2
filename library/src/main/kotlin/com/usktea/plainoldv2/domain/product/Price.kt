package com.usktea.plainoldv2.domain.product

import com.usktea.plainoldv2.exception.ErrorMessage
import jakarta.persistence.Embeddable

@Embeddable
data class Price(
    val amount: Long
) {
    init {
        require(amount >= 0) { ErrorMessage.PRICE_AMOUNT_EXCEPTION.value }
    }
}
