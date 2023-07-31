package com.usktea.plainoldv2.application.endpoints.healthCheck

import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class WelcomeHandler(
    val productRepository: ProductRepository
) {
    suspend fun welcome(request: ServerRequest): ServerResponse {
        //TODO 지워야한다.
        Product.fake().also {
            productRepository.save(it)
        }

        return ok().contentType(MediaType.TEXT_PLAIN).bodyValueAndAwait("welcome")
    }
}
