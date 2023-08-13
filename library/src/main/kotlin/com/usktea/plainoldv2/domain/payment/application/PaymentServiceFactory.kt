package com.usktea.plainoldv2.domain.payment.application

data class PaymentServiceFactory(
    private val services: MutableMap<String, PaymentService>
) {
    operator fun get(provider: String): PaymentService? {
        return services[provider]
    }
}
