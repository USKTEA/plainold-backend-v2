package com.usktea.plainoldv2.application.endpoints.cart

import com.usktea.plainoldv2.domain.cart.CartItemDto
import com.usktea.plainoldv2.domain.cart.CartItemsDto
import com.usktea.plainoldv2.domain.cart.application.CartService
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.RequestAttributeNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.attributeOrNull
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class CartHandler(
    private val cartService: CartService
) {

    suspend fun getItems(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()

        val items = cartService.getCartItems(username = username).map { CartItemDto.from(it) }

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(CartItemsDto(items))
    }
}
