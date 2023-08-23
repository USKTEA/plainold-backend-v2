package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.cart.*

fun createCartItem(): CartItem {
    return CartItem(
        productId = 1L,
        price = Price(1L),
        productName = ProductName("T-Shirts"),
        thumbnailUrl = ThumbnailUrl("1"),
        shippingFee = Price(1L),
        freeShippingAmount = Price(1L),
        quantity = Quantity(1L),
        itemOption = ItemOption(
            size = Size.M,
            color = "RED"
        )
    )
}

fun createAddCartItemRequest(): AddCartItemRequest {
    return AddCartItemRequest(
        items = listOf(
            CartItemDto(
                productId = 1L,
                price = 1L,
                name = "T-Shirts",
                thumbnailUrl = "1",
                shippingFee = 1L,
                freeShippingAmount = 1L,
                quantity = 1L,
                option = ItemOptionDto(
                    color = "RED",
                    size = "XL"
                )
            )
        )
    )
}

fun createUpdateCartItemRequest(): UpdateCartItemRequest {
    return UpdateCartItemRequest(
        items = listOf(
            CartItemDto(
                productId = 1L,
                price = 1L,
                name = "T-Shirts",
                thumbnailUrl = "1",
                shippingFee = 1L,
                freeShippingAmount = 1L,
                quantity = 1L,
                option = ItemOptionDto(
                    color = "RED",
                    size = "XL"
                )
            )
        )
    )
}
