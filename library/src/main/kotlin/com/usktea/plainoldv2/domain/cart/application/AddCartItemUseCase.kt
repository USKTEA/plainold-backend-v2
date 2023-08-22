package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.user.Username

interface AddCartItemUseCase {
    suspend fun addCartItems(username: Username, cartItems: List<CartItem>): Int
}
