package com.usktea.plainoldv2.application.endpoints.session

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class SessionRouter {
    val context = "/session"

    @Bean
    fun sessionRoutes(handler: SessionHandler) = coRouter {
        path(context).nest {
            POST("", handler::login)
        }
    }
}
