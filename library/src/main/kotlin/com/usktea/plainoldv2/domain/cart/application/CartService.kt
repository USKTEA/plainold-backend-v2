package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.Cart
import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.cart.repository.CartRepository
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.exception.UserNotExistsException
import org.springframework.stereotype.Service

@Service
class CartService(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) : GetCartItemUseCase, CreateCartUseCase {
    override suspend fun getCartItems(username: Username): List<CartItem> {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UserNotExistsException()
        val cart = cartRepository.findByUserIdOrNull(user.id) ?: createCart(userId = user.id)

        return cart.cartItems
    }

    override suspend fun createCart(userId: Long): Cart {
        return Cart(userId = userId).also { cartRepository.save(it) }
    }
}
