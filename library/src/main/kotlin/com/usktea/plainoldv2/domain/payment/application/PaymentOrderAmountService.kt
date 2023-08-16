package com.usktea.plainoldv2.domain.payment.application

import com.usktea.plainoldv2.domain.payment.OrderItemDto
import org.springframework.stereotype.Service

@Service
class PaymentOrderAmountService {
    fun calculate(orderItems: List<OrderItemDto>): Int {
        return getTotalAmount(orderItems)
    }

    private fun getTotalAmount(orderItems: List<OrderItemDto>): Int {
        val total = orderItems.sumOf { it.totalPrice }
        val largestShippingFee = orderItems.maxOfOrNull { it.shippingFee } ?: 0
        val largestFreeShippingAmount = orderItems.maxOfOrNull { it.freeShippingAmount } ?: 0

        if (total > largestFreeShippingAmount) {
            return total.toInt()
        }

        return (total + largestShippingFee).toInt()
    }
}
