package com.usktea.plainoldv2.application.config.exception

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler(
    errorAttributes: ErrorAttributes,
    resourceProperties: WebProperties.Resources,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    resourceProperties,
    applicationContext
) {
    init {
        setMessageWriters(serverCodecConfigurer.writers)
        setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes): RouterFunction<ServerResponse> {
        return RouterFunctions.route(RequestPredicates.all()) { request: ServerRequest ->
            renderErrorResponse(
                request,
                errorAttributes
            )
        }
    }

    private fun renderErrorResponse(
        request: ServerRequest,
        errorAttributes: ErrorAttributes
    ): Mono<ServerResponse> {
        val errorProperties = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults())
        val message = errorAttributes.getError(request).message
        errorProperties["message"] = message

        return ServerResponse.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(errorProperties))
    }
}

@Configuration
class ResourceWebPropertiesConfig {
    @Bean
    fun resources(): WebProperties.Resources {
        return WebProperties.Resources()
    }
}
