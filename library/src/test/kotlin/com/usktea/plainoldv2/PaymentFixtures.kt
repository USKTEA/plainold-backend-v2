package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.payment.OrderItemDto

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
