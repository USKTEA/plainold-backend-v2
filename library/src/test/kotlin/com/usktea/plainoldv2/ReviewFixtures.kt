package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.review.*
import com.usktea.plainoldv2.domain.user.Nickname
import com.usktea.plainoldv2.domain.user.Username

fun createFindReviewSpec(
    productId: Long,
    photoReviews: Boolean
): FindReviewSpec {
    return FindReviewSpec(
        productId, photoReviews
    )
}

fun createReview(
    reviewId: Long = 1L,
    productId: Long = 1L,
    orderNumber: String = "tjrxo1234-111111",
    username: String = "tjrxo1234@gmail.com"
): Review {
    return Review(
        id = reviewId,
        productId = productId,
        orderNumber = OrderNumber(orderNumber),
        reviewer = Reviewer(
            username = Username(username),
            nickname = Nickname("김뚜루")
        ),
        rate = Rate(5),
        comment = Comment("매우 좋습니다"),
        imageUrl = ImageUrl("1")
    )
}

fun createPostReviewRequest(
    orderNumber: String = "tjrxo1234-111111",
    productId: Long = 1L
): PostReviewRequest {
    return PostReviewRequest(
        productId = productId,
        orderNumber = OrderNumber(orderNumber),
        comment = Comment("너무 좋아요"),
        rate = Rate(5),
        imageUrl = ImageUrl("1")
    )
}

fun createEditReviewRequest(
    reviewId: Long = 1L,
    comment: Comment = Comment("너무 좋아요"),
    rate: Rate = Rate(5),
    imageUrl: ImageUrl = ImageUrl("1")
): EditReviewRequest {
    return EditReviewRequest(
        id = reviewId,
        comment = comment,
        rate = rate,
        imageUrl = imageUrl
    )
}
