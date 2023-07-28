package com.usktea.plainoldv2.application.endpoints.healthCheck

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class WelcomeRouter {

    @Bean
    fun welcomeRoutes(handler: WelcomeHandler) = coRouter {
        GET("/", handler::welcome)
    }
}
