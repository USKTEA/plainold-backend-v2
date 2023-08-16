package com.usktea.plainoldv2.domain.payment

data class PaymentReadyRequestDto(
    val provider: String,
    val orderItems: List<OrderItemDto>
)

data class PaymentReadyResponseDto(
    val paymentProvider: String,
    val prePaymentId: Long,
    val partnerOrderId: String,
    val redirectUrl: String
)

data class PaymentApproveRequest(
    val provider: String,
    val pgToken: String,
    val prePaymentId: Long,
    val partnerOrderId: String,
) {
    companion object {
        fun from(provider: String, pgToken: String, prePaymentId: Long, partnerOrderId: String): PaymentApproveRequest {
            return PaymentApproveRequest(
                provider = provider,
                pgToken = pgToken,
                prePaymentId = prePaymentId,
                partnerOrderId = partnerOrderId
            )
        }
    }
}

data class PaymentApproveResultDto(
    val approveCode: String
)

data class OrderItemDto(
    val productId: Long,
    val price: Long,
    val name: String,
    val thumbnailUrl: String,
    val shippingFee: Long,
    val freeShippingAmount: Long,
    val quantity: Long,
    val totalPrice: Long,
    val option: OrderOptionDto?
)

data class OrderOptionDto(
    val size: String,
    val color: String
)

data class KakaopayReadyResponse(
    val tid: String,
    val next_redirect_pc_url: String
)

data class KakaopayApproveResponse(
    val aid: String
)
