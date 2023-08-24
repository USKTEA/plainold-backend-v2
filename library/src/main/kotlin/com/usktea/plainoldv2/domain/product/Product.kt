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

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "product_id")
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
                productName = ProductName("Pants"),
                price = Price(2_000L),
                categoryId = 2L,
                image = Image(
                    productId = 2L,
                    thumbnailUrl = ThumbnailUrl("https://plainold.s3.amazonaws.com/product-image/10.jpg"),
                    productImageUrls = mutableSetOf(ProductImageUrl(imageId = 2L, value = "https://plainold.s3.amazonaws.com/product-image/10.jpg"))
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
