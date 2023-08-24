package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.user.Username

interface DeleteCartItemUseCase {
    suspend fun deleteItems(username: Username, cartItems: List<CartItem>): List<Long>
}
