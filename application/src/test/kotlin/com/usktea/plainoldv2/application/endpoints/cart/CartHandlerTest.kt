package com.usktea.plainoldv2.application.endpoints.cart

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createCartItem
import com.usktea.plainoldv2.application.createUsername
import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.cart.application.CartService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

const val USERNAME = "tjrxo1234@gmail.com"

@ActiveProfiles("test")
@WebFluxTest(CartRouter::class, CartHandler::class)
class CartHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @MockkBean
    private lateinit var cartService: CartService

    @Test
    fun `사용자의 카트를 조회한다`() {
        val username = createUsername(USERNAME)
        val cartItem = createCartItem()

        val token = jwtUtil.encode(USERNAME)

        coEvery { cartService.getCartItems(username) } returns listOf(cartItem)

        client.mutateWith(mockUser())
            .get()
            .uri("/carts")
            .header("Authorization", "Bearer $token")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.items").exists()
    }
}
