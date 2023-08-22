package com.usktea.plainoldv2.application.endpoints.cart

import com.usktea.plainoldv2.domain.cart.*
import com.usktea.plainoldv2.domain.cart.application.CartService
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.RequestAttributeNotFoundException
import com.usktea.plainoldv2.exception.RequestBodyNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class CartHandler(
    private val cartService: CartService
) {

    suspend fun getItems(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()
        val items = cartService.getCartItems(username = username).map { CartItemDto.from(it) }

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(CartItemsDto(items))
    }

    suspend fun addItems(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()
        val addCartItemRequest = request.awaitBodyOrNull<AddCartItemRequest>() ?: throw RequestBodyNotFoundException()
        val cartItems = addCartItemRequest.items.map { CartItem.from(it) }
        val counts = cartService.addCartItems(username = username, cartItems = cartItems)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(AddCartItemResult(counts))
    }
}
