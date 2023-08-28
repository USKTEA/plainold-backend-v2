package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.domain.review.EditReviewRequest
import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.domain.user.Username

interface EditReviewUseCase {
    suspend fun editReview(username: Username, editReviewRequest: EditReviewRequest): Review
}
