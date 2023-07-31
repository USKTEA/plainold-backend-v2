package com.usktea.plainoldv2.domain.category.application

import com.usktea.plainoldv2.domain.category.repository.CategoryRepository
import com.usktea.plainoldv2.createCategory
import io.kotest.matchers.collections.shouldBeSameSizeAs
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
class CategoryServiceTest {
    private val categoryRepository = mockk<CategoryRepository>()
    private val categoryService = CategoryService(categoryRepository)

    @Test
    fun `카테고리가 여러 개인 경우`() = runTest {
        val categories = listOf(
            createCategory(1L, "T-Shirts"),
            createCategory(2L, "Jeans"),
            createCategory(3L, "Shoes")
        )

        coEvery { categoryRepository.findAll() } returns categories

        val actual = categoryService.findAll()

        actual shouldBeSameSizeAs categories
    }
}
