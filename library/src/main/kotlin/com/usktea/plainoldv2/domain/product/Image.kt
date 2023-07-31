package com.usktea.plainoldv2.domain.product

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Embeddable

@Embeddable
data class Image(
    val thumbnailUrl: ThumbnailUrl,

    @ElementCollection
    val productImageUrls: MutableSet<ProductImageUrl>
)

@Embeddable
data class ThumbnailUrl(
    @Column(name = "thumbnailUrl")
    val value: String
)

@Embeddable
data class ProductImageUrl(
    @Column(name = "productImageUrl")
    val value: String
)
