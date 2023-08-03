package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.option.OptionData
import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.Product
import java.time.Instant

fun findProductSpec(categoryId: Long? = null, productId: Long? = null): FindProductSpec {
    return FindProductSpec(categoryId = categoryId, productId = productId)
}

fun createProduct(productId: Long, categoryId: Long): Product {
    return Product.fake().apply {
        this.id = productId
        this.categoryId = categoryId
        this.createdAt = Instant.now()
        this.updatedAt = Instant.now()
    }
}

fun createOption(productId: Long): OptionData {
    return OptionData.fake().apply {
        this.productId = productId
    }
}
