package com.usktea.plainoldv2.domain.order.application

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.user.Username

interface GetOrderCanWriteReviewUseCase {
    suspend fun getOrderCanWriteReview(username: Username, productId: Long): OrderNumber
}
