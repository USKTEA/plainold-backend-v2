package com.usktea.plainoldv2.application.endpoints.review

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class ReviewRouter {
    val context = "/reviews"

    @Bean
    fun reviewRoutes(handler: ReviewHandler) = coRouter {
        path(context).nest {
            GET("", handler::getReviews)
        }
    }
}
