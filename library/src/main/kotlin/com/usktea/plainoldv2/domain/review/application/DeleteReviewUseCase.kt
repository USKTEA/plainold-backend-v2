package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.domain.user.Username

interface DeleteReviewUseCase {
    suspend fun deleteReview(username: Username, reviewId: Long): Review
}
