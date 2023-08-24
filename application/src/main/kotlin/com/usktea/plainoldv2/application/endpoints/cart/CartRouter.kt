package com.usktea.plainoldv2.application.endpoints.cart

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class CartRouter {
    val context = "/carts"

    @Bean
    fun cartRoutes(handler: CartHandler) = coRouter {
        path(context).nest {
            GET("", handler::getItems)
            POST("", handler::addItems)
            PATCH("", handler::updateItems)
            POST("/delete", handler::deleteItems)
        }
    }
}
