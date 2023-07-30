package com.usktea.plainoldv2.domain.category.application

import com.usktea.plainoldv2.domain.category.CategoryDto
import com.usktea.plainoldv2.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    suspend fun findAll(): List<CategoryDto> {
        return categoryRepository.findAll().map {
            category -> CategoryDto.from(category)
        }
    }
}
