package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.review.*
import java.time.Instant

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
