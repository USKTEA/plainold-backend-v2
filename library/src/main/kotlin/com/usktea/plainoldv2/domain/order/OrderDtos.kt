package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.domain.user.Username

data class OrderRequestDto(
    val orderItems: List<OrderItemDto>,
    val orderer: OrdererDto,
    val shippingInformation: ShippingInformationDto,
    val payment: PaymentDto,
    val shippingFee: Long,
    val cost: Long
)

data class OrderItemDto(
    val productId: Long,
    val price: Long,
    val name: String,
    val thumbnailUrl: String,
    val shippingFee: Long,
    val freeShippingAmount: Long,
    val quantity: Long,
    val totalPrice: Long,
    val option: OrderOptionDto?
)

data class OrderOptionDto(
    val size: String,
    val color: String
)

data class OrdererDto(
    val name: String,
    val phoneNumber: String,
    val email: String
)

data class ReceiverDto(
    val name: String,
    val phoneNumber: String
) {
    companion object {
        fun from(receiver: Receiver): ReceiverDto {
            return ReceiverDto(
                name = receiver.name.value,
                phoneNumber = receiver.phoneNumber.value
            )
        }
    }
}

data class OrderAddressDto(
    val zipCode: String,
    val address1: String,
    val address2: String
) {
    companion object {
        fun from(address: Address): OrderAddressDto {
            return OrderAddressDto(
                zipCode = address.zipCode.value,
                address1 = address.address1.value,
                address2 = address.address2.value
            )
        }
    }
}

data class ShippingInformationDto(
    val receiver: ReceiverDto,
    val address: OrderAddressDto,
    val message: String = ""
)

data class PaymentDto(
    val method: String,
    val payer: String
)

data class OrderRequest(
    val username: Username,
    val orderLines: List<OrderLine>,
    val orderer: Orderer,
    val shippingInformation: ShippingInformation,
    val payment: Payment,
    val shippingFee: Price,
    val cost: Price
) {
    companion object {
        fun of(username: Username, orderRequestDto: OrderRequestDto): OrderRequest {
            return OrderRequest(
                username = username,
                orderLines = orderRequestDto.orderItems.map { OrderLine.from(it) },
                orderer = Orderer.from(orderRequestDto.orderer),
                shippingInformation = ShippingInformation.from(orderRequestDto.shippingInformation),
                payment = Payment.from(orderRequestDto.payment),
                shippingFee = Price.from(orderRequestDto.shippingFee),
                cost = Price.from(orderRequestDto.cost)
            )
        }
    }
}

data class OrderResultDto(
    val id: Long,
    val orderNumber: String,
    val cost: Long,
    val receiver: ReceiverDto,
    val shippingAddress: OrderAddressDto,
    val paymentMethod: String
) {
    companion object {
        fun from(order: Order): OrderResultDto {
            return OrderResultDto(
                id = order.id,
                orderNumber = order.orderNumber.value,
                cost = order.cost.amount,
                receiver = ReceiverDto.from(order.shippingInformation.receiver),
                shippingAddress = OrderAddressDto.from(order.shippingInformation.address),
                paymentMethod = order.payment.method.method
            )
        }
    }
}
