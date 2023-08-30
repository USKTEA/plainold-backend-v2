package com.usktea.plainoldv2.application.endpoints.file

import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.coRouter

@Component
class FileRouter {
    val context = "/files"

    @Bean
    fun fileRoutes(handler: FileHandler) = coRouter {
        path(context). nest {
            accept(MediaType.MULTIPART_FORM_DATA).nest {
                POST("", handler::upload)
            }
        }
    }
}
