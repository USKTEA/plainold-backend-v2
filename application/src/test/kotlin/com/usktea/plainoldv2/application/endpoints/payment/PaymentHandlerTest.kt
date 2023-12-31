package com.usktea.plainoldv2.application.endpoints.payment

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createPaymentReadyRequestDto
import com.usktea.plainoldv2.application.createPaymentReadyResponseDto
import com.usktea.plainoldv2.domain.oAuth.application.OAuthServiceFactory
import com.usktea.plainoldv2.domain.payment.application.KakaopayService
import com.usktea.plainoldv2.domain.payment.application.PaymentServiceFactory
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
const val PROVIDER = "KAKAO"
const val PG_TOKEN = "TOKEN"
const val PARTNER_ORDER_ID = "ID"
const val PRE_PAYMENT_ID = "1"
const val APPROVE_CODE = "APPROVE"

@ActiveProfiles("test")
@WebFluxTest(PaymentRouter::class, PaymentHandler::class)
class PaymentHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var paymentServiceFactory: PaymentServiceFactory

    @MockkBean
    private lateinit var kakaopayService: KakaopayService

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @Test
    fun `정확한 결제 준비 요청이 들어오면 준비 응답을 보내준다`() {
        val token = jwtUtil.encode(USERNAME)
        val paymentReadyRequest = createPaymentReadyRequestDto(PROVIDER)
        val paymentReadyResponse = createPaymentReadyResponseDto(PROVIDER)

        coEvery { paymentServiceFactory[PROVIDER] } returns kakaopayService
        coEvery { kakaopayService.ready(any(), any()) } returns paymentReadyResponse

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/payments")
            .header("Authorization", "Bearer $token")
            .bodyValue(paymentReadyRequest)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.prePaymentId").exists()
            .jsonPath("$.redirectUrl").exists()
    }

    @Test
    fun `정확한 결제 승인 요청이 들어오면 결제가 승인된다`() {
        val token = jwtUtil.encode(USERNAME)

        coEvery { paymentServiceFactory[PROVIDER] } returns kakaopayService
        coEvery { kakaopayService.approve(any(), any()) } returns APPROVE_CODE

        client.mutateWith(mockUser()).get()
            .uri("/payments?provider=$PROVIDER&pgToken=$PG_TOKEN&prePaymentId=$PRE_PAYMENT_ID&partnerOrderId=&$PARTNER_ORDER_ID")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.approveCode").isEqualTo(APPROVE_CODE)
    }
}

