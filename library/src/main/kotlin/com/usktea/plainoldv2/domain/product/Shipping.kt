package com.usktea.plainoldv2.domain.product

import jakarta.persistence.*

@Embeddable
data class Shipping(
    @Enumerated(EnumType.STRING)
    val shippingMethod: ShippingMethod,

    @AttributeOverride(name = "amount", column = Column(name = "shippingFee"))
    val shippingFee: Price,

    @AttributeOverride(name = "amount", column = Column(name = "freeShippingAmount"))
    val freeShippingAmount: Price
) {
}

enum class ShippingMethod(
    val method: String
) {
    Parcel("택배"), Post("우채국")
}
