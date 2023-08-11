package com.usktea.plainoldv2.application.endpoints.order

import com.usktea.plainoldv2.domain.order.OrderRequest
import com.usktea.plainoldv2.domain.order.OrderRequestDto
import com.usktea.plainoldv2.domain.order.application.OrderService
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.RequestAttributeNotFoundException
import com.usktea.plainoldv2.exception.RequestBodyNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import java.net.URI

@Component
class OrderHandler(
    private val orderService: OrderService
) {
    suspend fun createOrder(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()
        val orderRequest = request.awaitBodyOrNull<OrderRequestDto>()?.let {
            OrderRequest.of(username, it)
        } ?: throw RequestBodyNotFoundException()

        val orderResult = orderService.placeOrder(orderRequest)

        return created(URI.create(orderResult.id.toString()))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(orderResult)
    }
}
