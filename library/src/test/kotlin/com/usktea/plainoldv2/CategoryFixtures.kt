package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.category.Category

private const val CATEGORY_NAME = "T-Shirts"

fun createCategory(
    id: Long = 0L,
    name: String = CATEGORY_NAME
): Category {
    return Category(id, name)
}
