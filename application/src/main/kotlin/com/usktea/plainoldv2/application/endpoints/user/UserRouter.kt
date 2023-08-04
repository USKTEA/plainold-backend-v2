package com.usktea.plainoldv2.application.endpoints.user

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class UserRouter {
    val context = "/users"

    @Bean
    fun userRoutes(handler: UserHandler) = coRouter {
        path(context).nest {
            GET("/me", handler::findMe)
        }
    }
}
