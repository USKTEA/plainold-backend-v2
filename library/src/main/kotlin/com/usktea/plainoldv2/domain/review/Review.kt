package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.user.Nickname
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.ErrorMessage
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Entity
class Review(
    id: Long = 0L,

    val productId: Long,

    @Embedded
    val orderNumber: OrderNumber,

    @Embedded
    val reviewer: Reviewer,

    @Embedded
    var rate: Rate,

    @Embedded
    var comment: Comment,

    @Embedded
    var imageUrl: ImageUrl?
    ) : BaseEntity(id) {

    fun edit(username: Username, editReviewRequest: EditReviewRequest) {
        require(this.reviewer.username == username) { ErrorMessage.REVIEWER_NOT_MATCH }

        this.rate = editReviewRequest.rate
        this.comment = editReviewRequest.comment
        this.imageUrl = editReviewRequest.imageUrl
    }

    companion object {
        fun of(postReviewRequest: PostReviewRequest, username: Username, nickname: Nickname): Review {
            return Review(
                productId = postReviewRequest.productId,
                orderNumber = postReviewRequest.orderNumber,
                reviewer = Reviewer.of(
                    username = username,
                    nickname = nickname
                ),
                rate = postReviewRequest.rate,
                comment = postReviewRequest.comment,
                imageUrl = postReviewRequest.imageUrl
            )
        }
    }
}
