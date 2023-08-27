package com.usktea.plainoldv2.domain.order

import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "purchaseOrder")
class Order(
    id: Long = 0L,

    @Embedded
    @Access(AccessType.FIELD)
    val orderNumber: OrderNumber,

    @ElementCollection
    val orderLines: List<OrderLine>,

    @Embedded
    val username: Username,

    @Embedded
    val orderer: Orderer,

    @Embedded
    val shippingInformation: ShippingInformation,

    @Embedded
    val payment: Payment,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus,

    @Embedded
    @AttributeOverride(name = "amount", column = Column(name = "shippingFee"))
    val shippingFee: Price,

    @Embedded
    @AttributeOverride(name = "amount", column = Column(name = "cost"))
    val cost: Price,
) : BaseEntity(id) {
    init {
        if (this.payment.method == PaymentMethod.KAKAOPAY) {
            this.status = OrderStatus.PREPARING
        }
    }
    companion object {
        fun of(oderNumber: OrderNumber, orderRequest: OrderRequest): Order {
            return Order(
                orderNumber = oderNumber,
                orderLines = orderRequest.orderLines,
                username = orderRequest.username,
                orderer = orderRequest.orderer,
                shippingInformation = orderRequest.shippingInformation,
                payment = orderRequest.payment,
                status = OrderStatus.PAYMENT_WAITING,
                shippingFee = orderRequest.shippingFee,
                cost = orderRequest.cost
            )
        }
    }
}

@Embeddable
data class OrderNumber(
    @Column(name = "orderNumber")
    val value: String
) {
    companion object {
        fun from(number: String): OrderNumber {
            return OrderNumber(number)
        }
    }
}

enum class OrderStatus(
    val value: String
) {
    PAYMENT_WAITING("입금대기"),
    PREPARING("배송준비중"),
    SHIPPED("배송처리"),
    DELIVERING("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    CANCELED("취소완료");
}
