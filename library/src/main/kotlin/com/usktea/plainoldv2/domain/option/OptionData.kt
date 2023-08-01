package com.usktea.plainoldv2.domain.option

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity

@Entity
class OptionData(
    id: Long = 0L,
    val productId: Long,

    @ElementCollection
    val sizes: MutableSet<String>,

    @ElementCollection
    val colors: MutableSet<Color>
) : BaseEntity(id) {
    companion object {
        fun fake(): OptionData {
            return OptionData(
                productId = 1L,
                sizes = mutableSetOf(
                    "S", "M", "XL"
                ),
                colors = mutableSetOf(
                    Color(name = "Black", red = Rgb(0), green = Rgb(0), blue = Rgb(0))
                ),
            )
        }
    }
}
