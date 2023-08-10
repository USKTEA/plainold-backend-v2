package com.usktea.plainoldv2.domain.order

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Orderer(
    @AttributeOverride(name = "value", column = Column(name = "orderer"))
    val name: Name,

    @AttributeOverride(name = "value", column = Column(name = "ordererPhoneNumber"))
    val phoneNumber: PhoneNumber,

    @AttributeOverride(name = "value", column = Column(name = "email"))
    val email: Email
) {
    companion object {
        fun from(orderer: OrdererDto): Orderer {
            return Orderer(
                name = Name(orderer.name),
                phoneNumber = PhoneNumber(orderer.phoneNumber),
                email = Email(orderer.email)
            )
        }
    }
}
