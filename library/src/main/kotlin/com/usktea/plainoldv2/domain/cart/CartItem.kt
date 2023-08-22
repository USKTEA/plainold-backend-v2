package com.usktea.plainoldv2.domain.cart

import jakarta.persistence.*

@Embeddable
data class CartItem(
    val productId: Long,

    @AttributeOverride(name = "amount", column = Column(name = "price"))
    val price: Price,

    @AttributeOverride(name = "value", column = Column(name = "productName"))
    val productName: ProductName,

    @AttributeOverride(name = "value", column = Column(name = "thumbnailUrl"))
    val thumbnailUrl: ThumbnailUrl,

    @AttributeOverride(name = "amount", column = Column(name = "shippingFee"))
    val shippingFee: Price,

    @AttributeOverride(name = "amount", column = Column(name = "freeShippingAmount"))
    val freeShippingAmount: Price,

    @AttributeOverride(name = "amount", column = Column(name = "quantity"))
    val quantity: Quantity,

    @Embedded
    val itemOption: ItemOption?
) {
    fun checkIsSame(item: CartItem): Boolean {
        return this.productId == item.productId
                && this.price == item.price
                && this.productName == item.productName
                && this.thumbnailUrl == item.thumbnailUrl
                && this.shippingFee == item.shippingFee
                && this.freeShippingAmount == item.freeShippingAmount
                && this.itemOption == item.itemOption
    }

    fun increaseQuantity(quantity: Quantity): CartItem {
        return CartItem(
            productId = productId,
            price = price,
            productName = productName,
            thumbnailUrl = thumbnailUrl,
            shippingFee = shippingFee,
            freeShippingAmount = freeShippingAmount,
            quantity = this.quantity.add(quantity),
            itemOption = itemOption
        )
    }

    companion object {
        fun from(cartItemDto: CartItemDto): CartItem {
            return CartItem(
                productId = cartItemDto.productId,
                price = Price(cartItemDto.price),
                productName = ProductName(cartItemDto.name),
                thumbnailUrl = ThumbnailUrl(cartItemDto.thumbnailUrl),
                shippingFee = Price(cartItemDto.shippingFee),
                freeShippingAmount = Price(cartItemDto.freeShippingAmount),
                quantity = Quantity(cartItemDto.quantity),
                itemOption = cartItemDto.option?.let { ItemOption.from(cartItemDto?.option) }
            )
        }
    }
}

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
) {
    fun add(quantity: Quantity): Quantity {
        return Quantity(this.amount + quantity.amount)
    }
}

@Embeddable
data class ItemOption(
    @Access(AccessType.FIELD)
    @Enumerated(EnumType.STRING)
    val size: Size,

    @Access(AccessType.FIELD)
    val color: String
) {
    companion object {
        fun from(option: ItemOptionDto): ItemOption {
            return ItemOption(
                size = Size.valueOf(option.size),
                color = option.color
            )
        }
    }
}

enum class Size(
    val value: String
) {
    S("S"), M("M"), L("L"), XL("XL"), FREE("")
}
