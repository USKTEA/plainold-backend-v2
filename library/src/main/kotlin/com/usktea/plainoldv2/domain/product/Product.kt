package com.usktea.plainoldv2.domain.product

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.*

@Entity
class Product(
    id: Long = 0L,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "name"))
    val productName: ProductName,

    @Embedded
    @AttributeOverride(name = "amount", column = Column(name = "price"))
    val price: Price,

    var categoryId: Long = 0L,

    @Embedded
    val image: Image,

    @Embedded
    val description: ProductDescription,

    @Embedded
    val shipping: Shipping,

    @Enumerated(EnumType.STRING)
    val productStatus: ProductStatus
): BaseEntity(id) {
    companion object {
        fun fake(): Product {
            return Product(
                productName = ProductName("T-Shirts"),
                price = Price(1_000L),
                categoryId = 1L,
                image = Image(
                    thumbnailUrl = ThumbnailUrl("1"),
                    productImageUrls = mutableSetOf(ProductImageUrl("1"))
                ),
                description = ProductDescription(
                    summary = ProductSummary("Good"),
                    detail = ProductDetail("Very Good")
                ),
                shipping = Shipping(
                    shippingMethod = ShippingMethod.Post,
                    shippingFee = Price(1_000L),
                    freeShippingAmount = Price(50_000L)
                ),
                productStatus = ProductStatus.ON_SALE
            )
        }
    }
}
