package com.usktea.plainoldv2.domain.cart

import jakarta.persistence.*

@Embeddable
data class CartItem(
    val productId: Long,

    @AttributeOverride(name = "amount", column = Column(name = "price"))
    val price: Price,

    @AttributeOverride(name = "value", column = Column(name = "productName"))
    val productName: ProductName,

    @Embedded
    val thumbnailUrl: ThumbnailUrl,

    @AttributeOverride(name = "amount", column = Column(name = "shippingFee"))
    val shippingFee: Price,

    @AttributeOverride(name = "amount", column = Column(name = "freeShippingAmount"))
    val freeShippingAmount: Price,

    @AttributeOverride(name = "amount", column = Column(name = "quantity"))
    val quantity: Quantity,

    @Embedded
    val itemOption: ItemOption?
)

@Embeddable
data class Price(
    @Access(AccessType.FIELD)
    val amount: Long
)

@Embeddable
data class ProductName(
    @Access(AccessType.FIELD)
    val value: String
)

@Embeddable
data class ThumbnailUrl(
    @Access(AccessType.FIELD)
    val value: String
)

@Embeddable
data class Quantity(
    @Access(AccessType.FIELD)
    val amount: Long
)

@Embeddable
data class ItemOption(
    @Access(AccessType.FIELD)
    val size: Size,

    @Access(AccessType.FIELD)
    val color: String
)

enum class Size(
    val value: String
) {
    M("M"), L("L"), XL("XL"), FREE("")
}
