package com.usktea.plainoldv2.application.endpoints.category

import com.usktea.plainoldv2.domain.category.CategoriesDto
import com.usktea.plainoldv2.domain.category.application.CategoryService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class CategoryHandler(
    private val categoryService: CategoryService
) {
    suspend fun findAll(request: ServerRequest): ServerResponse {
        val categoryDtos = categoryService.findAll()

        return ok().contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(CategoriesDto(categoryDtos))
    }
}
