package com.usktea.plainoldv2.domain.product.application

import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.ProductDetailDto

interface GetProductDetailUseCase {
    suspend fun getProductDetail(productSpec: FindProductSpec): ProductDetailDto
}
