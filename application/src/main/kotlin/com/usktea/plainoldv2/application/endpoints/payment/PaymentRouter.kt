package com.usktea.plainoldv2.application.endpoints.payment

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class PaymentRouter {
    val context = "/payments"

    @Bean
    fun paymentRoutes(handler: PaymentHandler) = coRouter {
        path(context).nest {
            POST("", handler::ready)
            GET("", handler::approve)
        }
    }
}
