package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.order.PaymentMethod
import com.usktea.plainoldv2.domain.order.Price
import com.usktea.plainoldv2.domain.payment.OrderItemDto
import com.usktea.plainoldv2.domain.payment.Payment
import com.usktea.plainoldv2.domain.user.Username

fun createOrderItems(amount: Long, shippingFee: Long, freeShippingAmount: Long): List<OrderItemDto> {
    return listOf(OrderItemDto(
        productId = 1L,
        price = amount,
        name = "T-Shirts",
        thumbnailUrl = "1",
        shippingFee = shippingFee,
        freeShippingAmount = freeShippingAmount,
        quantity = 1,
        totalPrice = amount,
        option = null
    ))
}

fun createPayment(): Payment {
    return Payment(
        orderNumber = OrderNumber("tjrxo1234-111111"),
        username = Username("tjrxo1234@gmail.com"),
        method = PaymentMethod.KAKAOPAY,
        cost = Price(1_000L)
    )
}
