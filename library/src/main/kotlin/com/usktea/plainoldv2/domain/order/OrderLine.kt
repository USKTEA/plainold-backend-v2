package com.usktea.plainoldv2.domain.order

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
data class OrderLine(
    val productId: Long,

    @AttributeOverride(name = "value", column = Column(name = "productName"))
    val productName: ProductName,

    @AttributeOverride(name = "amount", column = Column(name = "price"))
    val price: Price,

    @AttributeOverride(name = "value", column = Column(name = "thumbnailUrl"))
    val thumbnailUrl: ThumbnailUrl,

    @AttributeOverride(name = "amount", column = Column(name = "quantity"))
    val quantity: Quantity,

    @AttributeOverride(name = "amount", column = Column(name = "totalPrice"))
    val totalPrice: Price,

    @Embedded
    val itemOption: ItemOption,
) {
    companion object {
        fun from(orderItem: OrderItemDto): OrderLine {
            return OrderLine(
                productId = orderItem.productId,
                productName = ProductName.from(orderItem.name),
                price = Price.from(orderItem.price),
                thumbnailUrl = ThumbnailUrl.from(orderItem.thumbnailUrl),
                quantity = Quantity.from(orderItem.quantity),
                totalPrice = Price.from(orderItem.totalPrice),
                itemOption = ItemOption.from(orderItem.option)
            )
        }
    }
}

@Embeddable
data class ProductName(
    val value: String
) {
    companion object {
        fun from(name: String): ProductName {
            return ProductName(name)
        }
    }
}

@Embeddable
data class Quantity(
    val amount: Long
) {
    companion object {
        fun from(amount: Long): Quantity {
            return Quantity(amount)
        }
    }
}

data class ItemOption(
    val color: String,
    val size: String
) {
    companion object {
        fun from(option: OrderOptionDto): ItemOption {
            return ItemOption(color = option.color, size = option.size)
        }
    }
}

@Embeddable
data class ThumbnailUrl(
    val value: String
) {
    companion object {
        fun from(url: String): ThumbnailUrl {
            return ThumbnailUrl(url)
        }
    }
}

@Embeddable
data class Price(
    val amount: Long
) {
    companion object {
        fun from(amount: Long): Price {
            return Price(amount)
        }
    }
}
