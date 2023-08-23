package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.domain.cart.Cart
import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.cart.repository.CartRepository
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.exception.CartNotFoundException
import com.usktea.plainoldv2.exception.UserNotExistsException
import org.springframework.stereotype.Service

@Service
class CartService(
    private val userRepository: UserRepository,
    private val cartRepository: CartRepository
) : GetCartItemUseCase, CreateCartUseCase, AddCartItemUseCase, UpdateCartItemUseCase {
    override suspend fun getCartItems(username: Username): List<CartItem> {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UserNotExistsException()
        val cart = cartRepository.findByUserIdOrNull(user.id) ?: Cart.NULL

        return cart.cartItems
    }

    override suspend fun createCart(userId: Long): Cart {
        return Cart(userId = userId).also { cartRepository.save(it) }
    }

    override suspend fun addCartItems(username: Username, cartItems: List<CartItem>): Int {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UserNotExistsException()
        val cart = cartRepository.findByUserIdOrNull(user.id)

        if (cart == null) {
            val cart = Cart(userId = user.id)

            cart.addItems(cartItems)

            cartRepository.save(cart)

            return cart.countItems()
        }

        cart.addItems(cartItems)
        cartRepository.update(cart)

        return cart.countItems()
    }

    override suspend fun updateItems(username: Username, cartItems: List<CartItem>): List<Long> {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UserNotExistsException()
        val cart = cartRepository.findByUserIdOrNull(user.id) ?: throw CartNotFoundException()

        val updatedIds = cart.updateItems(items = cartItems)

        cartRepository.update(cart)

        return updatedIds
    }
}
