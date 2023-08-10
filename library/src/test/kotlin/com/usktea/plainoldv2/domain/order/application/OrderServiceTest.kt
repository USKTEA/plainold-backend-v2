package com.usktea.plainoldv2.domain.order.application

import com.usktea.plainoldv2.createOrder
import com.usktea.plainoldv2.createOrderRequest
import com.usktea.plainoldv2.domain.order.repository.OrderRepository
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class OrderServiceTest {
    private val orderRepository = mockk<OrderRepository>()
    private val orderService = OrderService(orderRepository)

    @Test
    fun `올바른 요청이 들어오면 주문을 생성하고 결과를 반환한다`() = runTest {
        val orderRequest = createOrderRequest()
        val order = createOrder()

        coEvery { orderRepository.save(any()) } returns order

        val orderResult = orderService.placeOrder(orderRequest)

        coVerify(exactly = 1) { orderRepository.save(any()) }

        orderResult shouldNotBe null
    }
}
