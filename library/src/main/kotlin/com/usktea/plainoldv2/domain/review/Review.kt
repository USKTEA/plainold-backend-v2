package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.user.Nickname
import com.usktea.plainoldv2.domain.user.Role
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.ErrorMessage
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

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
    var imageUrl: ImageUrl?,

    @Enumerated(EnumType.STRING)
    var status: ReviewStatus = ReviewStatus.ACTIVE
) : BaseEntity(id) {

    fun edit(username: Username, editReviewRequest: EditReviewRequest) {
        require(this.reviewer.username == username) { ErrorMessage.REVIEWER_NOT_MATCH.value }

        this.rate = editReviewRequest.rate
        this.comment = editReviewRequest.comment
        this.imageUrl = editReviewRequest.imageUrl
    }

    fun delete(username: Username, role: Role) {
        require(this.reviewer.username == username || role == Role.ADMIN) { ErrorMessage.NOT_HAVE_PERMISSION.value }

        this.status = ReviewStatus.DELETED
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
