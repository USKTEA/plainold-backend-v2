package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.Cart
import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.user.Username

interface GetCartItemUseCase {
    suspend fun getCartItems(username: Username): List<CartItem>
}
