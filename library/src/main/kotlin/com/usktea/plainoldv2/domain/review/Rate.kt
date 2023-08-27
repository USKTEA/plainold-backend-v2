package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.exception.ErrorMessage
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Rate(
    @Column(name = "rate")
    val value: Int
) {
    init {
        require(value in 1..5) { ErrorMessage.INVALID_RATE.value }
    }

    companion object {
        fun from(value: Int): Rate {
            return Rate(value)
        }
    }
}
