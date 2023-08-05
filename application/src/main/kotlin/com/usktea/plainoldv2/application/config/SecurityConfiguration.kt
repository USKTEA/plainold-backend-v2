package com.usktea.plainoldv2.application.config

import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.utils.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration {
    @Bean
    fun webHttpSecurity(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            authorizeExchange {
                authorize(anyExchange, permitAll)
            }
            httpBasic { disable() }
            formLogin { disable() }
            cors { disable() }
            csrf { disable() }
        }
    }
}

@Component
class RequestContextWebFilter(
    private val jwtUtil: JwtUtil
) : WebFilter {
    companion object {
        private const val BEARER_PREFIX = "Bearer "
    }

    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> {
        val accessToken = exchange.request.headers.getFirst("authorization") ?: return chain.filter(exchange)

        if (!accessToken.startsWith(BEARER_PREFIX)) {
            return chain.filter(exchange)
        }

        val username = accessToken.toUsername()

        exchange.attributes["username"] = username

        return chain.filter(exchange)
    }

    private fun String.toUsername(): Username {
        return Username(
            this.substring(BEARER_PREFIX.length).let { jwtUtil.decode(it) }
        )
    }
}
