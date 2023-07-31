package com.usktea.plainoldv2.domain.product

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class ProductDescription(
    val summary: ProductSummary,
    val detail: ProductDetail
)

@Embeddable
data class ProductSummary(
    @Column(name = "productSummary")
    val content: String
)

@Embeddable
data class ProductDetail(
    @Column(name = "productDetail")
    val content: String
)
