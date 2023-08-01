package com.usktea.plainoldv2.application.endpoints.product

import com.ninjasquad.springmockk.MockkBean
import com.usktea.plainoldv2.application.fakeProduct
import com.usktea.plainoldv2.application.productDetailDto
import com.usktea.plainoldv2.domain.product.application.ProductService
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(ProductRouter::class, ProductHandler::class)
@ActiveProfiles("test")
class ProductHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var productService: ProductService

    @Test
    fun `페이징 조건에 맞는 모든 상품을 조회한다`() {
        val categoryId = 1L
        val products = PageImpl(
            listOf(
                fakeProduct(productId = 1L, categoryId = categoryId),
                fakeProduct(productId = 2L, categoryId = categoryId),
                fakeProduct(productId = 3L, categoryId = categoryId)
            )
        )

        coEvery { productService.findAllByPaging(any(), any()) } returns products

        client.mutateWith(mockUser()).get()
            .uri("/products?categoryId=1&page=1")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.products.length()").isEqualTo(3)
            .jsonPath("$.products[0].id").isEqualTo(1L)
            .jsonPath("$.products[1].id").isEqualTo(2L)
            .jsonPath("$.products[2].id").isEqualTo(3L)
    }

    @Test
    fun `id에 맞는 상품 정보을 조회한다`() {
        val productDetail = productDetailDto(id = 1L)

        coEvery { productService.getProductDetail(productId = 1L) } returns productDetail

        client.mutateWith(mockUser()).get()
            .uri("/products/1")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isEqualTo(1L)
    }
}
