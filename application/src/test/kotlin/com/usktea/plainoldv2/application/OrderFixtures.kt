package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.order.*

fun createOrderRequestDto(): OrderRequestDto {
    return OrderRequestDto(
        orderItems = listOf(
            OrderItemDto(
                productId = 1L,
                price = 1_000L,
                name = "T-Shirts",
                thumbnailUrl = "1",
                shippingFee = 1_000L,
                freeShippingAmount = 1_000L,
                quantity = 1L,
                totalPrice = 1_000L,
                option = OrderOptionDto(
                    size = "XL",
                    color = "RED"
                )
            )
        ),
        orderer = OrdererDto(
            name = "김뚜루",
            phoneNumber = "010-1111-1111",
            email = "tjrxo1234@gmail.com"
        ),
        shippingInformation = ShippingInformationDto(
            receiver = ReceiverDto(
                name = "김뚜루",
                phoneNumber = "010-1111-1111"
            ),
            address = OrderAddressDto(
                zipCode = "111111",
                address1 = "서울시 성동구 상원12길 34",
                address2 = "에이원지식산업센터"
            ),
            message = "조심히 와주세요"
        ),
        payment = PaymentDto(
            method = "CASH",
            payer = "김뚜루"
        ),
        shippingFee = 0L,
        cost = 1_000L
    )
}

fun createOrderResult(): OrderResultDto {
    return OrderResultDto(
        id = 1L,
        orderNumber = "tjrxo1234-111111",
        cost = 1_000L,
        receiver = ReceiverDto(
            name = "김뚜루",
            phoneNumber = "010-1111-1111"
        ),
        shippingAddress = OrderAddressDto(
            zipCode = "111111",
            address1 = "서울시 성동구 상원12길 34",
            address2 = "에이원지식산업센터"
        ),
        paymentMethod = PaymentMethod.CASH.method
    )
}

fun createOrderNumber(number: String): OrderNumber {
    return OrderNumber(number)
}
