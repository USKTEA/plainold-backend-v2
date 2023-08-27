package com.usktea.plainoldv2.application.endpoints.order

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class OrderRouter {
    val context = "/orders"
    
    @Bean
    fun orderRoutes(handler: OrderHandler) = coRouter { 
        path(context).nest {
            POST("", handler::createOrder)
            GET("", handler::getOrderCanWriteReview)
        }
    }
}
