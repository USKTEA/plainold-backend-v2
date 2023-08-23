package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.user.Username

interface UpdateCartItemUseCase {
    suspend fun updateItems(username: Username, cartItems: List<CartItem>): List<Long>
}
