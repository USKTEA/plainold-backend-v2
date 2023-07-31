package com.usktea.plainoldv2.application.endpoints.product

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class ProductRouter {
    val context = "/products"

    @Bean
    fun productRoutes(handler: ProductHandler) = coRouter {
        path(context).nest {
            GET("", handler::findAllByPaging)
        }
    }
}
