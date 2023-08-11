package com.usktea.plainoldv2.domain.option

import com.usktea.plainoldv2.exception.ErrorMessage
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Color(
    val name: String,

    @AttributeOverride(name = "value", column = Column(name = "red"))
    val red: Rgb,

    @AttributeOverride(name = "value", column = Column(name = "green"))
    val green: Rgb,

    @AttributeOverride(name = "value", column = Column(name = "blue"))
    val blue: Rgb,
)

@Embeddable
data class Rgb(
    var value: Int
) {
    init {
        require(value in 0..255) { ErrorMessage.RGB_VALUE_EXCEPTION.value }
    }
}
