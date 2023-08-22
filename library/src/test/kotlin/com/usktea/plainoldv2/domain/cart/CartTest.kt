package com.usktea.plainoldv2.domain.cart

import com.usktea.plainoldv2.createCart
import com.usktea.plainoldv2.createCartItem
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

const val USER_ID = 1L

@ActiveProfiles("test")
class CartTest {

    @Test
    fun `카트에 상품을 추가하면 상품이 저장된다`() {
        val cart = createCart(USER_ID, mutableListOf())
        val cartItem = createCartItem()
        val cartItems = mutableListOf(cartItem)

        cart.countItems() shouldBe 0

        cart.addItems(cartItems)

        cart.countItems() shouldBe 1
    }

    @Test
    fun `카트에 동일한 상품을 추가하면 상품의 수량이 갱신된다`() {
        val cart = createCart(USER_ID, mutableListOf())
        val cartItem = createCartItem()
        val cartItems = mutableListOf(cartItem)

        cart.countItems() shouldBe 0

        cart.addItems(cartItems)

        cart.countItems() shouldBe 1
        cart.firstItem().quantity shouldBe Quantity(1L)

        cart.addItems(cartItems)
        cart.countItems() shouldBe 1
        cart.firstItem().quantity shouldBe Quantity(2L)
    }
}


