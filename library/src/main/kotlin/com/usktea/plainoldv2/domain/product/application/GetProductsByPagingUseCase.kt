package com.usktea.plainoldv2.domain.product.application

import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GetProductsByPagingUseCase {
    suspend fun findAllByPaging(bookSpec: FindProductSpec, pageable: Pageable): Page<Product>
}
