package com.usktea.plainoldv2.application.endpoints.category

import com.ninjasquad.springmockk.MockkBean
import com.usktea.plainoldv2.application.categoryDto
import com.usktea.plainoldv2.domain.category.CategoriesDto
import com.usktea.plainoldv2.domain.category.application.CategoryService
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest(CategoryRouter::class, CategoryHandler::class)
@ActiveProfiles("test")
class CategoryHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var categoryService: CategoryService

    @Test
    fun `모든 카테고리를 조회한다`() {
        val categories = listOf(
            categoryDto(id = 1L, name = "T-Shirts"),
            categoryDto(id = 2L, name = "Jeans"),
            categoryDto(id = 3L, name = "Shoes"),
        )

        coEvery { categoryService.findAll() } returns categories

        val categoriesDto = CategoriesDto(categories)
        val responses = client.mutateWith(mockUser()).get()
            .uri("/categories")
            .exchange()
            .expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(CategoriesDto::class.java)
            .returnResult()
            .responseBody

        responses shouldBe categoriesDto
    }
}
