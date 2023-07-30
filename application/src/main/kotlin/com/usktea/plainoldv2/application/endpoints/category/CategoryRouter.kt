package com.usktea.plainoldv2.application.endpoints.category

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class CategoryRouter {
    val context = "/categories"

    @Bean
    fun categoryRoutes(handler: CategoryHandler) = coRouter {
        path(context).nest {
            GET("", handler::findAll)
        }
    }
}
