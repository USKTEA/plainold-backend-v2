package com.usktea.plainoldv2.application.endpoints.payment

import com.usktea.plainoldv2.domain.payment.PaymentApproveRequest
import com.usktea.plainoldv2.domain.payment.PaymentApproveResultDto
import com.usktea.plainoldv2.domain.payment.PaymentReadyRequestDto
import com.usktea.plainoldv2.domain.payment.application.PaymentServiceFactory
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.PaymentProviderNotFoundException
import com.usktea.plainoldv2.exception.RequestAttributeNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

@Component
class PaymentHandler(
    private val paymentServiceFactory: PaymentServiceFactory
) {
    suspend fun ready(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()
        val paymentReadyRequestDto = request.awaitBody<PaymentReadyRequestDto>()
        val paymentService =
            paymentServiceFactory[paymentReadyRequestDto.provider] ?: throw PaymentProviderNotFoundException()

        val paymentReadyResponseDto = paymentService.ready(
            username = username, orderItems = paymentReadyRequestDto.orderItems
        )

        return created(URI.create(paymentReadyResponseDto.prePaymentId.toString())).contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(paymentReadyResponseDto)
    }

    suspend fun approve(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()
        val provider = request.queryParamOrNull("provider") ?: throw RequestAttributeNotFoundException()
        val pgToken = request.queryParamOrNull("pgToken") ?: throw RequestAttributeNotFoundException()
        val prePaymentId =
            request.queryParamOrNull("prePaymentId")?.toLong() ?: throw RequestAttributeNotFoundException()
        val partnerOrderId = request.queryParamOrNull("partnerOrderId") ?: throw RequestAttributeNotFoundException()

        val paymentApproveRequest = PaymentApproveRequest.from(
            provider = provider,
            pgToken = pgToken,
            prePaymentId = prePaymentId,
            partnerOrderId = partnerOrderId
        )

        val paymentService = paymentServiceFactory[provider] ?: throw PaymentProviderNotFoundException()

        val aid = paymentService.approve(username = username, paymentApproveRequest = paymentApproveRequest)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(PaymentApproveResultDto(approveCode = aid))
    }
}
