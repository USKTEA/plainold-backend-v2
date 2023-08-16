package com.usktea.plainoldv2.domain.cart

data class CartItemsDto(
    val items: List<CartItemDto>
)

data class CartItemDto(
    val productId: Long,
    val price: Long,
    val name: String,
    val thumbnailUrl: String,
    val shippingFee: Long,
    val freeShippingAmount: Long,
    val quantity: Long,
    val option: ItemOptionDto?
) {
    companion object {
        fun from(cartItem: CartItem): CartItemDto {
            return CartItemDto(
                productId = cartItem.productId,
                price = cartItem.price.amount,
                name = cartItem.productName.value,
                thumbnailUrl = cartItem.thumbnailUrl.value,
                shippingFee = cartItem.shippingFee.amount,
                freeShippingAmount = cartItem.freeShippingAmount.amount,
                quantity = cartItem.quantity.amount,
                option = cartItem.itemOption?.let { ItemOptionDto.from(it) }
            )
        }
    }
}

data class ItemOptionDto(
    val size: String,
    val color: String
) {
    companion object {
        fun from(itemOption: ItemOption): ItemOptionDto {
            return ItemOptionDto(
                size = itemOption.size.value,
                color = itemOption.color
            )
        }
    }
}
