package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.support.PageDto

data class FindReviewSpec(
    val productId: Long,
    val photoReviews: Boolean
)

data class ReviewDto(
    val id: Long,
    val productId: Long,
    val reviewer: ReviewerDto,
    val rate: Int,
    val comment: String,
    val imageUrl: String?,
    val createdAt: String
) {
    companion object {
        fun from(review: Review): ReviewDto {
            return ReviewDto(
                id = review.id,
                productId = review.productId,
                reviewer = ReviewerDto.from(review.reviewer),
                rate = review.rate.value,
                comment = review.comment.value,
                imageUrl = review.imageUrl?.value,
                createdAt = review.createdAt()
            )
        }
    }
}

data class ReviewerDto(
    val username: String,
    val nickname: String
) {
    companion object {
        fun from(reviewer: Reviewer): ReviewerDto {
            return ReviewerDto(
                username = reviewer.username.value,
                nickname = reviewer.nickname.value
            )
        }
    }
}

data class ReviewsDto(
    val reviews: List<ReviewDto>,
    val page: PageDto
)

data class PostReviewRequestDto(
    val productId: Long,
    val orderNumber: String,
    val comment: String,
    val rate: Int,
    val imageUrl: String?
)

data class PostReviewResultDto(
    val reviewId: Long
)

data class PostReviewRequest(
    val productId: Long,
    val orderNumber: OrderNumber,
    val comment: Comment,
    val rate: Rate,
    val imageUrl: ImageUrl?
) {
    companion object {
        fun from(postReviewRequestDto: PostReviewRequestDto): PostReviewRequest {
            return PostReviewRequest(
                productId = postReviewRequestDto.productId,
                orderNumber = OrderNumber.from(postReviewRequestDto.orderNumber),
                comment = Comment.from(postReviewRequestDto.comment),
                rate = Rate.from(postReviewRequestDto.rate),
                imageUrl = postReviewRequestDto.imageUrl?.let { ImageUrl.from(it) }
            )
        }
    }
}
