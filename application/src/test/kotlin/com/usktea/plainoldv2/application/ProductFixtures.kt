package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.product.*
import java.time.Instant

fun fakeProduct(productId: Long, categoryId: Long): Product {
    return Product.fake().apply {
        this.id = productId
        this.categoryId = categoryId
        this.createdAt = Instant.now()
        this.updatedAt = Instant.now()
    }
}

fun productDetailDto(id: Long): ProductDetailDto {
    return ProductDetailDto(
        id = id,
        price = 1_000L,
        name = "T-Shirts",
        categoryId = 1L,
        image = ImageDto(
            thumbnailUrl = "1",
            productImageUrls = arrayOf("1")
        ),
        shipping = ShippingDto(
            shippingMethod = ShippingMethod.Parcel.method,
            shippingFee = 1_000L,
            freeShippingAmount = 1_000L,
        ),
        description = ProductDescriptionDto(
            productSummary = "Good",
            productDetail = "Very Good"
        ),
        optionData = null,
        status = ProductStatus.ON_SALE.status,
        createdAt = Instant.now(),
        updatedAt = Instant.now()
    )
}
