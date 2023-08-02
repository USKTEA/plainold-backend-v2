package com.usktea.plainoldv2.domain.product.application

import com.usktea.plainoldv2.domain.option.repository.OptionRepository
import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.domain.product.ProductDetailDto
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import com.usktea.plainoldv2.exception.PRODUCT_NOT_FOUND
import com.usktea.plainoldv2.exception.ProductNotFound
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val optionRepository: OptionRepository
) : GetProductsByPagingUseCase, GetProductDetailUseCase {
    override suspend fun findAllByPaging(productSpec: FindProductSpec, pageable: Pageable): Page<Product> {
        return productRepository.findAllByPaging(productSpec, pageable)
    }

    override suspend fun getProductDetail(productSpec: FindProductSpec): ProductDetailDto {
        try {
            val product = productRepository.findBySpec(productSpec)
            val option = optionRepository.findByProductIdOrNull(productSpec.productId!!)

            return ProductDetailDto.from(product, option)
        } catch (exception: Exception) {
            throw ProductNotFound(PRODUCT_NOT_FOUND)
        }
    }
}
