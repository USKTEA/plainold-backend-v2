package com.usktea.plainoldv2.domain.payment

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.order.PaymentMethod
import com.usktea.plainoldv2.domain.order.Price
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Payment(
    id: Long = 0L,

    @Embedded
    val orderNumber: OrderNumber,

    @Embedded
    val username: Username,

    @Enumerated(EnumType.STRING)
    val method: PaymentMethod,

    @Embedded
    val cost: Price,

    @Enumerated(EnumType.STRING)
    val status: PaymentStatus = if (method == PaymentMethod.CASH) PaymentStatus.INIT else PaymentStatus.COMPLETED
) : BaseEntity(id) {
    companion object {
        fun of(orderNumber: OrderNumber, username: Username, method: PaymentMethod, cost: Price): Payment {
            return Payment(orderNumber = orderNumber, username = username, method = method, cost = cost)
        }
    }
}

enum class PaymentStatus {
    INIT, COMPLETED, CANCELLED, REFUND
}
