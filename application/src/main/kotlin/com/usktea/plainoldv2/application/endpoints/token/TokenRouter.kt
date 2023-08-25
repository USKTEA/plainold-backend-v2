package com.usktea.plainoldv2.application.endpoints.token

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class TokenRouter {
    val context = "/tokens"

    @Bean
    fun tokenRoutes(handler: TokenHandler) = coRouter {
        path(context).nest {
            POST("", handler::reissueToken)
        }
    }
}
