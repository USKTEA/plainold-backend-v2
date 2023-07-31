package com.usktea.plainoldv2.domain.category.application

import com.usktea.plainoldv2.domain.category.CategoryDto

interface GetCategoriesUseCase {
    suspend fun findAll(): List<CategoryDto>
}
