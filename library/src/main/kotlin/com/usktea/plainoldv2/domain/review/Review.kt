package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.domain.order.OrderNumber
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
    val rate: Rate,

    @Embedded
    val comment: Comment,

    @Embedded
    val imageUrl: ImageUrl?,

    ) : BaseEntity(id)


