package com.usktea.plainoldv2.domain.payment

import com.usktea.plainoldv2.exception.ErrorMessage
import jakarta.persistence.*

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
    val itemOption: ItemOption?,
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
                itemOption = orderItem.option?.let { ItemOption.from(orderItem.option) } ?: ItemOption.default()
            )
        }
    }
}

@Embeddable
data class Quantity(
    @Access(AccessType.FIELD)
    val amount: Long
) {
    init {
        require(amount > 0L) { ErrorMessage.INVALID_QUANTITY_AMOUNT.value }
    }

    companion object {
        fun from(amount: Long): Quantity {
            return Quantity(amount)
        }
    }
}

@Embeddable
data class ProductName(
    @Access(AccessType.FIELD)
    val value: String
) {
    companion object {
        fun from(name: String): ProductName {
            return ProductName(name)
        }
    }
}

@Embeddable
data class ItemOption(
    @Access(AccessType.FIELD)
    val color: String,

    @Access(AccessType.FIELD)
    val size: String
) {
    companion object {
        fun from(option: OrderOptionDto): ItemOption {
            return ItemOption(color = option.color, size = option.size)
        }

        fun default(): ItemOption {
            return ItemOption(color = "", size = "FREE")
        }
    }
}

@Embeddable
data class ThumbnailUrl(
    @Access(AccessType.FIELD)
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
    @Access(AccessType.FIELD)
    val amount: Long
) {
    init {
        require(amount >= 0) { ErrorMessage.PRICE_AMOUNT_EXCEPTION.value }
    }

    companion object {
        fun from(amount: Long): Price {
            return Price(amount)
        }
    }
}
