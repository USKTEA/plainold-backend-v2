package com.usktea.plainoldv2.domain.order.application

import com.usktea.plainoldv2.createOrder
import com.usktea.plainoldv2.createOrderRequest
import com.usktea.plainoldv2.createUsername
import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.order.repository.OrderRepository
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

const val USERNAME = "tjrxo1234@gmail.com"
const val PRODUCT_ID = 1L
const val ORDER_NUMBER = "tjrxo1234-111111"

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

    @Test
    fun `구매평 작성이 가능한 주문 번호를 반환한다`() = runTest {
        val username = createUsername(USERNAME)
        val orderNumber = OrderNumber(ORDER_NUMBER)

        coEvery { orderRepository.findOrderCanWriteReview(username, PRODUCT_ID) } returns orderNumber

        val found = orderService.getOrderCanWriteReview(username, PRODUCT_ID)

        found shouldBe orderNumber
    }
}
