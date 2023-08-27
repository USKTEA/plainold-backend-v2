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

fun createReview(productId: Long): Review {
    return Review(
        id = 1L,
        productId = productId,
        orderNumber = OrderNumber("11"),
        reviewer = Reviewer(
            username = Username("tjrxo1234@gmail.com"),
            nickname = Nickname("김뚜루")
        ),
        rate = Rate(5),
        comment = Comment("매우 좋습니다"),
        imageUrl = ImageUrl("1")
    )
}
