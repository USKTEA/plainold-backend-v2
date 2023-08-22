package com.usktea.plainoldv2.domain.cart.application

import com.usktea.plainoldv2.*
import com.usktea.plainoldv2.domain.cart.repository.CartRepository
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

const val USERNAME = "tjrxo1234@gmail.com"
const val PASSWORD = "Password1234!"

@ActiveProfiles("test")
class CartServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val cartRepository = mockk<CartRepository>()

    private val cartService = CartService(userRepository, cartRepository)

    @Test
    fun `사용자 카트를 조회한다`() = runTest {
        val username = createUsername(USERNAME)
        val password = createPassword(PASSWORD)
        val cartItem = createCartItem()

        val user = createUser(username, password)
        val cart = createCart(
            userId = user.id,
            cartItems = mutableListOf(cartItem)
        )

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { cartRepository.findByUserIdOrNull(user.id) } returns cart

        val cartItems = cartService.getCartItems(username)

        cartItems shouldHaveSize 1
        cartItems shouldBeEqual mutableListOf(cartItem)
    }

    @Test
    fun `사용자 카트가 생성되지 않았다면 빈 배열을 반환한다`() = runTest {
        val username = createUsername(USERNAME)
        val password = createPassword(PASSWORD)

        val user = createUser(username, password)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { cartRepository.findByUserIdOrNull(user.id) } returns null

        val cartItems = cartService.getCartItems(username)

        cartItems shouldHaveSize 0
    }

    @Test
    fun `사용자 카트에 상품을 추가한다`() = runTest {
        val username = createUsername(USERNAME)
        val password = createPassword(PASSWORD)
        val user = createUser(username, password)

        val cart = createCart(userId = user.id, cartItems = mutableListOf())
        val cartItem = createCartItem()
        val cartItems = mutableListOf(cartItem)
        val updated = createCart(userId = user.id, cartItems = cartItems)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { cartRepository.findByUserIdOrNull(user.id) } returns cart
        coEvery { cartRepository.update(any(), any()) } returns updated

        cart.isEmpty() shouldBe true

        cartService.addCartItems(username = username, cartItems = cartItems)

        coVerify(exactly = 1) { cartRepository.update(any(), any()) }
        updated.isEmpty() shouldBe false
        updated.countItems() shouldBe 1
    }
}
