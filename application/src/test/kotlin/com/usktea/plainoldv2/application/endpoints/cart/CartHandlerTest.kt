package com.usktea.plainoldv2.application.endpoints.cart

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createAddCartItemRequest
import com.usktea.plainoldv2.application.createCartItem
import com.usktea.plainoldv2.application.createUpdateCartItemRequest
import com.usktea.plainoldv2.application.createUsername
import com.usktea.plainoldv2.domain.cart.CartItem
import com.usktea.plainoldv2.domain.cart.application.CartService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

const val USERNAME = "tjrxo1234@gmail.com"
const val PRODUCT_ID = 1L

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

    @Test
    fun `사용자 카트에 아이템을 추가한다`() {
        val username = createUsername(USERNAME)
        val token = jwtUtil.encode(USERNAME)
        val addCartItemRequest = createAddCartItemRequest()

        coEvery { cartService.addCartItems(username, any()) } returns 1

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/carts")
            .header("Authorization", "Bearer $token")
            .bodyValue(addCartItemRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.counts").isEqualTo(1)
    }

    @Test
    fun `사용자 카트의 아이템을 수정한다`() {
        val username = createUsername(USERNAME)
        val token = jwtUtil.encode(USERNAME)
        val updateCartItemRequest = createUpdateCartItemRequest()

        coEvery { cartService.updateItems(username, any()) } returns listOf(PRODUCT_ID)

        client.mutateWith(csrf()).mutateWith(mockUser())
            .patch()
            .uri("/carts")
            .header("Authorization", "Bearer $token")
            .bodyValue(updateCartItemRequest)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.updated").isNotEmpty
    }
}
