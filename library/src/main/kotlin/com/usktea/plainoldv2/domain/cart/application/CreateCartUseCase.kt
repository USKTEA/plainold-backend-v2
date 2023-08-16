package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.Cart

interface CreateCartUseCase {
    suspend fun createCart(userId: Long): Cart
}
