package com.usktea.plainoldv2.domain.payment

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class PrePayment(
    id: Long = 0L,

    val userId: Long,

    val tid: String,

    @ElementCollection
    val orderItems: List<OrderLine>,

    @Enumerated(EnumType.STRING)
    val status: PrePaymentStatus = PrePaymentStatus.UNUSED
) : BaseEntity()


enum class PrePaymentStatus{
    UNUSED, USED
}
