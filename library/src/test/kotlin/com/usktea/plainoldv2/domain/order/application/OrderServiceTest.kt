package com.usktea.plainoldv2.domain.order.application

import com.usktea.plainoldv2.createOrder
import com.usktea.plainoldv2.createOrderRequest
import com.usktea.plainoldv2.createPayment
import com.usktea.plainoldv2.createUsername
import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.order.repository.OrderRepository
import com.usktea.plainoldv2.domain.payment.repository.PaymentRepository
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
    private val paymentRepository = mockk<PaymentRepository>()
    private val orderService = OrderService(orderRepository, paymentRepository)

    @Test
    fun `올바른 요청이 들어오면 주문과 결제 정보를 생성하고 결과를 반환한다`() = runTest {
        val orderRequest = createOrderRequest()
        val order = createOrder()
        val payment = createPayment()

        coEvery { orderRepository.save(any()) } returns order
        coEvery { paymentRepository.save(any()) } returns payment

        val orderResult = orderService.placeOrder(orderRequest)

        coVerify(exactly = 1) { orderRepository.save(any()) }
        coVerify(exactly = 1) { paymentRepository.save(any()) }

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
