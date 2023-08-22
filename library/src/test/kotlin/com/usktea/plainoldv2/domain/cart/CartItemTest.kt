package com.usktea.plainoldv2.domain.cart

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class CartItemTest {

    @Test
    fun `수량을 제외한 필드 값이 일치하다면 true를 반환한다`() {
        val cartItem1 = CartItem(
            productId = 1L,
            price = Price(1L),
            productName = ProductName("T-shirts"),
            thumbnailUrl = ThumbnailUrl("1"),
            shippingFee = Price(1L),
            freeShippingAmount = Price(1L),
            quantity = Quantity(1L),
            itemOption = ItemOption(
                color = "RED",
                size = Size.XL
            )
        )

        val cartItem2 = CartItem(
            productId = 1L,
            price = Price(1L),
            productName = ProductName("T-shirts"),
            thumbnailUrl = ThumbnailUrl("1"),
            shippingFee = Price(1L),
            freeShippingAmount = Price(1L),
            quantity = Quantity(2L),
            itemOption = ItemOption(
                color = "RED",
                size = Size.XL
            )
        )

        cartItem1.checkIsSame(cartItem2) shouldBe true
    }

    @Test
    fun `수량이 추가된 CartItem을 반환한다`() {
        val cartItem = CartItem(
            productId = 1L,
            price = Price(1L),
            productName = ProductName("T-shirts"),
            thumbnailUrl = ThumbnailUrl("1"),
            shippingFee = Price(1L),
            freeShippingAmount = Price(1L),
            quantity = Quantity(1L),
            itemOption = ItemOption(
                color = "RED",
                size = Size.XL
            )
        )

        val quantity = Quantity(2L)
        val increased = cartItem.increaseQuantity(quantity)

        increased.quantity shouldBe Quantity(3L)
    }
}
