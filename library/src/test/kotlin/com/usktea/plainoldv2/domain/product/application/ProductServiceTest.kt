package com.usktea.plainoldv2.domain.product.application

import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import com.usktea.plainoldv2.fakeProduct
import com.usktea.plainoldv2.findProductSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class ProductServiceTest {
    private val productRepository = mockk<ProductRepository>()
    private val productService = ProductService(productRepository)

    @Test
    fun `페이징 조건에 맞는 모든 상품을 조회한다`() = runTest {
        val products = PageImpl(listOf(fakeProduct(productId = 1L, categoryId = 1L)))
        val findProductSpec = findProductSpec(categoryId = 1L)
        val pageable = PageRequest.of(0, 8, Sort.by("id").descending())

        coEvery { productRepository.findAllByPaging(any(), any()) } returns products

        val found = productService.findAllByPaging(bookSpec = findProductSpec, pageable = pageable)

        found shouldHaveSize 1
    }
}
