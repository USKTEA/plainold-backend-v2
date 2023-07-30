package com.usktea.plainoldv2.domain.category

data class CategoryDto(
    val id: Long,
    val name: String
) {
    companion object {
        fun from(category: Category): CategoryDto {
            return CategoryDto(
                id = category.id,
                name = category.name
            )
        }
    }
}

data class CategoriesDto(
    val categories: List<CategoryDto>
)
