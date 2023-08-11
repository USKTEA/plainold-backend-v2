package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.order.*
import com.usktea.plainoldv2.domain.order.OrderRequest
import com.usktea.plainoldv2.domain.user.Username

fun createOrderRequest(): OrderRequest {
    return OrderRequest(
        username = Username("tjrxo1234@gmail.com"),
        orderLines = listOf(
            OrderLine(
            productId = 1L,
            productName = ProductName("T-Shirt"),
            price = Price(1_000L),
            thumbnailUrl = ThumbnailUrl("1"),
            quantity = Quantity(1L),
            totalPrice = Price(1_000L),
            itemOption = ItemOption(
                color = "RED",
                size = "XL"
            )
        )
        ),
        orderer = Orderer(
            name = Name("김뚜루"),
            phoneNumber = PhoneNumber("010-1111-1111"),
            email = Email("tjrxo1234@gmail.com")
        ),
        shippingInformation = ShippingInformation(
            receiver = Receiver(
                name = Name("김뚜루"),
                phoneNumber = PhoneNumber("010-1111-1111")
            ),
            address = Address(
                zipCode = ZipCode("111111"),
                address1 = Address1("서울시 성동구 상원12길 34"),
                address2 = Address2("에이원지식산업센터")
            ),
            message = "조심히 와주세요"
        ),
        payment = Payment(
            method = PaymentMethod.CASH,
            payer = Name("김뚜루")
        ),
        shippingFee = Price(1_000L),
        cost = Price(2_000L)
    )
}

fun createOrder(): Order {
    return Order(
        id = 1L,
        orderNumber = OrderNumber("tjrxo1234"),
        username = Username("tjrxo1234@gmail.com"),
        orderLines = listOf(
            OrderLine(
                productId = 1L,
                productName = ProductName("T-Shirt"),
                price = Price(1_000L),
                thumbnailUrl = ThumbnailUrl("1"),
                quantity = Quantity(1L),
                totalPrice = Price(1_000L),
                itemOption = ItemOption(
                    color = "RED",
                    size = "XL"
                )
            )
        ),
        orderer = Orderer(
            name = Name("김뚜루"),
            phoneNumber = PhoneNumber("010-1111-1111"),
            email = Email("tjrxo1234@gmail.com")
        ),
        shippingInformation = ShippingInformation(
            receiver = Receiver(
                name = Name("김뚜루"),
                phoneNumber = PhoneNumber("010-1111-1111")
            ),
            address = Address(
                zipCode = ZipCode("111111"),
                address1 = Address1("서울시 성동구 상원12길 34"),
                address2 = Address2("에이원지식산업센터")
            ),
            message = "조심히 와주세요"
        ),
        payment = Payment(
            method = PaymentMethod.CASH,
            payer = Name("김뚜루")
        ),
        status = OrderStatus.PAYMENT_WAITING,
        shippingFee = Price(1_000L),
        cost = Price(2_000L)
    )
}
