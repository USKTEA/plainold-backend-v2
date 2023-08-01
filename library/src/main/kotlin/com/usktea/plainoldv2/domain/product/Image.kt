package com.usktea.plainoldv2.domain.product

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.*

@Entity
class Image(
    id: Long = 0L,

    @Column(name = "product_id")
    val productId: Long,

    @Embedded
    val thumbnailUrl: ThumbnailUrl,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    val productImageUrls: MutableSet<ProductImageUrl>
) : BaseEntity(id)

@Embeddable
data class ThumbnailUrl(
    @Column(name = "thumbnailUrl")
    val value: String
)

@Entity
class ProductImageUrl(
    id: Long = 0L,

    @Column(name = "image_id")
    val imageId: Long,

    @Column(name = "productImageUrl")
    val value: String
) : BaseEntity(id)
