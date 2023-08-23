package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.cart.*

fun createCartItem(productId: Long = 1L, quantity: Long = 1L): CartItem {
    return CartItem(
        productId = productId,
        price = Price(1L),
        productName = ProductName("T-Shirts"),
        thumbnailUrl = ThumbnailUrl("1"),
        shippingFee = Price(1L),
        freeShippingAmount = Price(1L),
        quantity = Quantity(quantity),
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
