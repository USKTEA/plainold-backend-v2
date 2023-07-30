package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.category.CategoryDto

private const val CATEGORY_NAME = "T-Shirts"

fun categoryDto(
    id: Long = 0L,
    name: String = CATEGORY_NAME
): CategoryDto {
    return CategoryDto(id, name)
}
