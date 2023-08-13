package com.usktea.plainoldv2.domain.payment.application

import com.usktea.plainoldv2.domain.payment.OrderItemDto
import com.usktea.plainoldv2.domain.payment.PaymentReadyResponseDto
import com.usktea.plainoldv2.domain.user.Username

interface PaymentService {
    suspend fun ready(username: Username, orderItems: List<OrderItemDto>): PaymentReadyResponseDto
}
