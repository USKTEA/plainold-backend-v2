package com.usktea.plainoldv2

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

fun createCart(userId: Long, cartItems: MutableList<CartItem> = mutableListOf()): Cart {
    return Cart(
        userId = userId,
        cartItems = cartItems
    )
}
