package com.usktea.plainoldv2.domain.product.application

import com.usktea.plainoldv2.domain.option.repository.OptionRepository
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import com.usktea.plainoldv2.fakeOption
import com.usktea.plainoldv2.fakeProduct
import com.usktea.plainoldv2.findProductSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class ProductServiceTest {
    private val productRepository = mockk<ProductRepository>()
    private val optionRepository = mockk<OptionRepository>()
    private val productService = ProductService(productRepository, optionRepository)

    @Test
    fun `페이징 조건에 맞는 모든 상품을 조회한다`() = runTest {
        val products = PageImpl(listOf(fakeProduct(productId = 1L, categoryId = 1L)))
        val findProductSpec = findProductSpec(categoryId = 1L)
        val pageable = PageRequest.of(0, 8, Sort.by("id").descending())

        coEvery { productRepository.findAllByPaging(any(), any()) } returns products

        val found = productService.findAllByPaging(productSpec = findProductSpec, pageable = pageable)

        found shouldHaveSize 1
    }

    @Test
    fun `id에 맞는 상품과 상품의 옵션 정보를 조회한다`() = runTest {
        val product = fakeProduct(productId = 1L, categoryId = 1L)
        val option = fakeOption(productId = 1L)
        val findProductSpec = findProductSpec(productId = 1L)

        coEvery { productRepository.findBySpec(findProductSpec) } returns product
        coEvery { optionRepository.findByProductId(productId = 1L) } returns option

        val productDetail = productService.getProductDetail(findProductSpec)

        productDetail.id shouldBe 1L
    }
}
