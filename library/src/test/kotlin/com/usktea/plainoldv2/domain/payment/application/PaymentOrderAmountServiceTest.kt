package com.usktea.plainoldv2.domain.payment.application

import com.usktea.plainoldv2.createOrderItems
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class PaymentOrderAmountServiceTest {
    private val paymentOrderAmountService = PaymentOrderAmountService()

    @Test
    fun `주문 금액이 무료 배송 금액보다 큰 경우 배송 금액을 추가하지 않는다`() {
        val orderItems = createOrderItems(amount = 50_000, shippingFee = 10_000, freeShippingAmount = 30_000)

        val amount = paymentOrderAmountService.calculate(orderItems)

        amount shouldBe 50_000
    }

    @Test
    fun `주문 금액이 무료 배송 금액보다 적은 경우 배송 금액을 추가한다`() {
        val orderItems = createOrderItems(amount = 20_000, shippingFee = 10_000, freeShippingAmount = 30_000)

        val amount = paymentOrderAmountService.calculate(orderItems)

        amount shouldBe 20_000 + 10_000
    }
}
