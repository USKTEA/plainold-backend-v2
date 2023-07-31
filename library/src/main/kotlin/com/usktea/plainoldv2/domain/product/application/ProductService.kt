package com.usktea.plainoldv2.domain.product.application

import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) : GetProductsByPagingUseCase {
    override suspend fun findAllByPaging(bookSpec: FindProductSpec, pageable: Pageable): Page<Product> {
        return productRepository.findAllByPaging(bookSpec, pageable)
    }
}
