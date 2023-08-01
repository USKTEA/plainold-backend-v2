package com.usktea.plainoldv2.domain.option

data class OptionDataDto(
    val sizes: MutableSet<String>,
    val colors: MutableSet<ColorDto>
) {
    companion object {
        fun of(optionData: OptionData): OptionDataDto {
            return OptionDataDto(
                sizes = optionData.sizes,
                colors = optionData.colors.map {
                    ColorDto.of(it)
                }.toMutableSet()
            )
        }
    }
}

data class ColorDto(
    val name: String,
    val red: Int,
    val green: Int,
    val blue: Int
) {
    companion object {
        fun of(color: Color): ColorDto {
            return ColorDto(
                name = color.name,
                red = color.red.value,
                green = color.green.value,
                blue = color.blue.value
            )
        }
    }
}
