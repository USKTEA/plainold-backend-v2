package com.usktea.plainoldv2.application.endpoints.product

import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.ProductDto
import com.usktea.plainoldv2.domain.product.ProductsDto
import com.usktea.plainoldv2.domain.product.application.ProductService
import com.usktea.plainoldv2.support.PageDto
import io.netty.util.internal.StringUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

@Component
class ProductHandler(
    private val productService: ProductService
) {
    suspend fun findAllByPaging(request: ServerRequest): ServerResponse {
        val categoryId = request.queryParamOrNull("categoryId").let {
            when (!StringUtil.isNullOrEmpty(it)) {
                true -> it!!.toLong()
                false -> null
            }
        }
        val pageNumber = request.queryParamOrNull("page")?.toInt() ?: 1
        val sortBy = request.queryParamOrNull("sort") ?: "id"
        val pageable = PageRequest.of(pageNumber - 1, 8, Sort.by(sortBy).descending())

        val found = productService.findAllByPaging(FindProductSpec(categoryId = categoryId), pageable)
        val products = found.content.map { ProductDto.from(it) }
        val page = PageDto(pageNumber, found.totalPages, found.totalElements)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(
            ProductsDto(products = products, page = page)
        )
    }

    suspend fun findById(request: ServerRequest): ServerResponse {
        val productId = requireNotNull(request.pathVariable("id")).toLong()
        val productDetail = productService.getProductDetail(FindProductSpec(productId = productId))

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(productDetail)
    }
}
