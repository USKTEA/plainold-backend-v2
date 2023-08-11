package com.usktea.plainoldv2.domain.order.application

import com.usktea.plainoldv2.domain.order.OrderRequest
import com.usktea.plainoldv2.domain.order.OrderResultDto

interface PlaceOrderUseCase {
    suspend fun placeOrder(orderRequest: OrderRequest): OrderResultDto
}
