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
    var status: PrePaymentStatus = PrePaymentStatus.UNUSED
) : BaseEntity() {
    fun isUnused(): Boolean {
        return this.status == PrePaymentStatus.UNUSED
    }

    fun toStatus(status: PrePaymentStatus) {
        this.status = status
    }
}

enum class PrePaymentStatus{
    UNUSED, USED, FAILED
}
