package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.payment.OrderItemDto
import com.usktea.plainoldv2.domain.payment.OrderOptionDto
import com.usktea.plainoldv2.domain.payment.PaymentReadyRequestDto
import com.usktea.plainoldv2.domain.payment.PaymentReadyResponseDto

fun createPaymentReadyRequestDto(provider: String): PaymentReadyRequestDto {
    return PaymentReadyRequestDto(
        provider = provider,
        orderItems = listOf(
            OrderItemDto(
                productId = 1L,
                price = 5_000L,
                name = "T-Shirts",
                thumbnailUrl = "1",
                shippingFee = 1_000L,
                freeShippingAmount = 1_000L,
                quantity = 1L,
                totalPrice = 5_000L,
                option = OrderOptionDto(
                    size = "XL",
                    color = "RED"
                )
            )
        )
    )
}

fun createPaymentReadyResponseDto(provider: String): PaymentReadyResponseDto {
    return PaymentReadyResponseDto(
        paymentProvider = provider,
        prePaymentId = 1L,
        partnerOrderId = "Something",
        redirectUrl = "www.some.com"
    )
}
