package com.usktea.plainoldv2.domain.order.application

import com.usktea.plainoldv2.domain.order.Order
import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.order.OrderRequest
import com.usktea.plainoldv2.domain.order.OrderResultDto
import com.usktea.plainoldv2.domain.order.repository.OrderRepository
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.OrderCanWriteReviewNotFoundException
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class OrderService(
    private val orderRepository: OrderRepository
) : PlaceOrderUseCase, GetOrderCanWriteReviewUseCase {
    override suspend fun placeOrder(orderRequest: OrderRequest): OrderResultDto {
        val orderNumber = createOrderNumber(orderRequest.username)

        val order = Order.of(orderNumber, orderRequest).also {
            orderRepository.save(it)
        }

        return OrderResultDto.from(order)
    }

    private fun createOrderNumber(username: Username): OrderNumber {
        return OrderNumber(username.afterAt() + "-" + time())
    }

    private fun time(): String {
        val current = Calendar.getInstance().time
        val date = SimpleDateFormat("yyyyMMddHHmm")

        return date.format(current)
    }

    override suspend fun getOrderCanWriteReview(username: Username, productId: Long): OrderNumber {
        return orderRepository.findOrderCanWriteReview(username, productId)
            ?: throw OrderCanWriteReviewNotFoundException()
    }
}
