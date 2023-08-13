package com.usktea.plainoldv2.domain.payment.application

import com.usktea.plainoldv2.domain.payment.*
import com.usktea.plainoldv2.domain.payment.repository.PrePaymentRepository
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.exception.UserNotExistsException
import com.usktea.plainoldv2.properties.KakaopayProperties
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.time.LocalDateTime

@Service
class KakaopayService(
    val properties: KakaopayProperties,
    val paymentOrderAmountService: PaymentOrderAmountService,
    val prePaymentRepository: PrePaymentRepository,
    val userRepository: UserRepository,
    val client: WebClient
) : PaymentService {
    override suspend fun ready(
        username: Username,
        orderItems: List<OrderItemDto>
    ): PaymentReadyResponseDto {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UserNotExistsException()
        val partnerOrderId = getPartnerOrderId()

        val response = client.post()
            .uri(properties.readyUri)
            .header("Authorization", "KakaoAK ${properties.adminKey}")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(readyRequest(user, partnerOrderId, orderItems))
            .retrieve()
            .awaitBody<KakaopayReadyResponse>()

        val prePayment = PrePayment(
            userId = user.id,
            tid = response.tid,
            orderItems = orderItems.map { OrderLine.from(it) }
        ).also { prePaymentRepository.save(it) }

        return PaymentReadyResponseDto(
            paymentProvider = properties.paymentProvider,
            prePaymentId = prePayment.id,
            partnerOrderId = partnerOrderId,
            redirectUrl = response.next_redirect_pc_url
        )
    }

    private fun readyRequest(
        user: User,
        partnerOrderId: String,
        orderItems: List<OrderItemDto>
    ): MultiValueMap<String, String> {
        val formData = LinkedMultiValueMap<String, String>()
        val orderAmount = paymentOrderAmountService.calculate(orderItems)

        formData["cid"] = properties.cid
        formData["partner_order_id"] = partnerOrderId
        formData["partner_user_id"] = user.id.toString()
        formData["item_name"] = getItemName(orderItems)
        formData["quantity"] = orderItems.size.toString()
        formData["total_amount"] = orderAmount.toString()
        formData["tax_free_amount"] = properties.texFreeAmount
        formData["approval_url"] = properties.approvalUrl
        formData["cancel_url"] = properties.cancelUrl
        formData["fail_url"] = properties.failUrl

        return formData
    }

    private fun getItemName(orderItems: List<OrderItemDto>): String {
        val itemName = orderItems[0].name

        if (orderItems.size > 1) {
            return "$itemName 외 상품 (${orderItems.size})개"
        }

        return itemName
    }

    private fun getPartnerOrderId(): String {
        return LocalDateTime.now().toString()
    }
}
