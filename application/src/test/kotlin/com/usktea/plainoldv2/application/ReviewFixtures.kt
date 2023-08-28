package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.review.*
import com.usktea.plainoldv2.domain.user.Nickname
import com.usktea.plainoldv2.domain.user.Username

fun createReview(reviewId: Long, productId: Long): Review {
    return Review(
        id = reviewId,
        productId = productId,
        orderNumber = OrderNumber("11"),
        reviewer = Reviewer(
            username = Username("tjrxo1234@gmail.com"),
            nickname = Nickname("김뚜루")
        ),
        rate = Rate(5),
        comment = Comment("매우 좋습니다"),
        imageUrl = ImageUrl("1"),
    )
}

fun createPostReviewRequestDto(): PostReviewRequestDto {
    return PostReviewRequestDto(
        orderNumber = "tjrxo1234-111111",
        productId = 1L,
        comment = "아주 좋습니다",
        rate = 5,
        imageUrl = "1"
    )
}

fun createEditReviewRequestDto(): EditReviewRequestDto {
    return EditReviewRequestDto(
        id = 1L,
        comment = "아주 좋습니다",
        rate = 5,
        imageUrl = "1"
    )
}
