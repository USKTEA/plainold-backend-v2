package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.product.Product
import java.time.Instant

fun fakeProduct(productId: Long, categoryId: Long): Product {
    return Product.fake().apply {
        this.id = productId
        this.categoryId = categoryId
        this.createdAt = Instant.now()
        this.updatedAt = Instant.now()
    }
}
