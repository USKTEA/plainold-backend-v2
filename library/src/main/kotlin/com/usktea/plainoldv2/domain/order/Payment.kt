package com.usktea.plainoldv2.domain.order

import jakarta.persistence.*

@Embeddable
data class Payment(
    @Enumerated(EnumType.STRING)
    val method: PaymentMethod,

    @AttributeOverride(name = "value", column = Column(name = "payer"))
    val payer: Name
) {
    companion object {
        fun from(paymentDto: PaymentDto): Payment {
            return Payment(
                method = PaymentMethod.valueOf(paymentDto.method),
                payer = Name(paymentDto.payer)
            )
        }
    }

}

enum class PaymentMethod(
    val method: String
) {
    CASH("CASH"), KAKAOPAY("KAKAOPAY")
}
