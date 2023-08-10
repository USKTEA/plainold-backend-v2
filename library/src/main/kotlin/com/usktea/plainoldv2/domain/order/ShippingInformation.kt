package com.usktea.plainoldv2.domain.order

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class ShippingInformation(
    val receiver: Receiver,
    val address: Address,
    val message: String
) {
    companion object {
        fun from(shippingInformationDto: ShippingInformationDto): ShippingInformation {
            return ShippingInformation(
                receiver = Receiver.from(shippingInformationDto.receiver),
                address = Address.from(shippingInformationDto.address),
                message = shippingInformationDto.message
            )
        }
    }
}

@Embeddable
data class Address(
    val zipCode: ZipCode,
    val address1: Address1,
    val address2: Address2
) {
    companion object {
        fun from(address: OrderAddressDto): Address {
            return Address(
                zipCode = ZipCode(address.zipCode),
                address1 = Address1(address.address1),
                address2 = Address2(address.address2)
            )
        }
    }
}

@Embeddable
data class Address2(
    @Column(name = "address2")
    val value: String
)

@Embeddable
data class Address1(
    @Column(name = "address1")
    val value: String
)

@Embeddable
class ZipCode(
    @Column(name = "zipCode")
    val value: String
)

@Embeddable
data class Receiver(
    @AttributeOverride(name = "value", column = Column(name = "receiver"))
    val name: Name,

    @AttributeOverride(name = "value", column = Column(name = "receiverPhoneNumber"))
    val phoneNumber: PhoneNumber
) {
    companion object {
        fun from(receiver: ReceiverDto): Receiver {
            return Receiver(
                name = Name(receiver.name),
                phoneNumber = PhoneNumber(receiver.phoneNumber)
            )
        }
    }
}
