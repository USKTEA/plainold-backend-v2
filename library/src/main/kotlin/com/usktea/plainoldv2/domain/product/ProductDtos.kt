package com.usktea.plainoldv2.domain.product

import com.fasterxml.jackson.annotation.JsonInclude
import com.usktea.plainoldv2.domain.option.OptionData
import com.usktea.plainoldv2.domain.option.OptionDataDto
import com.usktea.plainoldv2.support.PageDto
import java.time.Instant

data class FindProductSpec(
    val categoryId: Long? = null,
    val productId: Long? = null,
)

data class ProductDto(
    val id: Long,
    val name: String,
    val price: Long,
    val categoryId: Long?,
    val thumbnailUrl: String,
) {
    companion object {
        fun from(product: Product): ProductDto {
            return ProductDto(
                id = product.id,
                name = product.productName.value,
                price = product.price.amount,
                categoryId = product.categoryId,
                thumbnailUrl = product.image.thumbnailUrl.value,
            )
        }
    }
}

data class ProductsDto(
    val products: List<ProductDto>,
    val page: PageDto
)

data class ProductDetailDto(
    val id: Long,
    val price: Long,
    val name: String,
    val categoryId: Long,
    val image: ImageDto,
    val description: ProductDescriptionDto,
    val shipping: ShippingDto,
    val status: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val optionData: OptionDataDto?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun from(product: Product, optionData: OptionData?): ProductDetailDto {
            return ProductDetailDto(
                id = product.id,
                price = product.price.amount,
                name = product.productName.value,
                categoryId = product.categoryId,
                image = ImageDto.of(product.image),
                description = ProductDescriptionDto.of(product.description),
                shipping = ShippingDto.of(product.shipping),
                status = product.productStatus.status,
                createdAt = product.createdAt,
                updatedAt = product.updatedAt,
                optionData = optionData?.let { OptionDataDto.of(it) }
            )
        }
    }
}

data class ImageDto(
    val thumbnailUrl: String,
    val productImageUrls: Array<String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (javaClass != other?.javaClass) {
            return false
        }

        other as ImageDto

        if (thumbnailUrl != other.thumbnailUrl) {
            return false
        }

        return productImageUrls.contentEquals(other.productImageUrls)
    }

    override fun hashCode(): Int {
        var result = thumbnailUrl.hashCode()
        result = 31 * result + productImageUrls.contentHashCode()
        return result
    }

    companion object {
        fun of(image: Image): ImageDto {
            return ImageDto(
                thumbnailUrl = image.thumbnailUrl.value,
                productImageUrls = image.productImageUrls.map {
                    it.value
                }.toTypedArray()
            )
        }
    }
}

data class ShippingDto(
    val shippingMethod: String,
    val shippingFee: Long,
    val freeShippingAmount: Long
) {
    companion object {
        fun of(shipping: Shipping): ShippingDto {
            return ShippingDto(
                shippingMethod = shipping.shippingMethod.method,
                shippingFee = shipping.shippingFee.amount,
                freeShippingAmount = shipping.freeShippingAmount.amount
            )
        }
    }
}

data class ProductDescriptionDto(
    val productSummary: String,
    val productDetail: String
) {
    companion object {
        fun of(description: ProductDescription): ProductDescriptionDto {
            return ProductDescriptionDto(
                productSummary = description.summary.content,
                productDetail = description.detail.content
            )
        }
    }
}
