package com.usktea.plainoldv2.application.endpoints.order

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createOrderRequestDto
import com.usktea.plainoldv2.application.createOrderResult
import com.usktea.plainoldv2.domain.order.application.OrderService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

const val USERNAME = "tjrxo1234@gmail.com"

@ActiveProfiles("test")
@WebFluxTest(OrderRouter::class, OrderHandler::class)
class OrderHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @MockkBean
    private lateinit var orderService: OrderService

    @Test
    fun `올바른 주문 요청이 들어오면 주문을 생성한다`() {
        val token = jwtUtil.encode(USERNAME)
        val orderRequest = createOrderRequestDto()
        val orderResult = createOrderResult()

        coEvery { orderService.placeOrder(any()) } returns orderResult

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/orders")
            .header("Authorization", "Bearer $token")
            .bodyValue(orderRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.orderNumber").exists()
    }
}
