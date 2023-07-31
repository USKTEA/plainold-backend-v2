package com.usktea.plainoldv2.domain.product

import com.usktea.plainoldv2.support.PageDto

data class FindProductSpec(
    val categoryId: Long?
)

data class ProductDto(
    val id: Long,
    val name: String,
    val price: Long,
    val categoryId: Long?,
    val thumbnailUrl: String
) {
    companion object {
        fun from(product: Product): ProductDto {
            return ProductDto(
                id = product.id,
                name = product.productName.value,
                price = product.price.amount,
                categoryId = product.categoryId,
                thumbnailUrl = product.image.thumbnailUrl.value
            )
        }
    }
}

data class ProductsDto(
    val products: List<ProductDto>,
    val page: PageDto
)
