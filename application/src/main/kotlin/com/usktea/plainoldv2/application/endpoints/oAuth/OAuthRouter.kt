package com.usktea.plainoldv2.application.endpoints.oAuth

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class OAuthRouter {
    val context = "oauth"

    @Bean
    fun oAuthRoutes(handler: OAuthHandler) = coRouter {
        path(context).nest {
            GET("{provider}", handler::getRedirectUrl)
            POST("/session", handler::login)
        }
    }
}
