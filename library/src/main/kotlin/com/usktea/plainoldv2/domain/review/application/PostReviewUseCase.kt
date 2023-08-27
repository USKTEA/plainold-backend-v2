package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.domain.review.PostReviewRequest
import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.domain.user.Username

interface PostReviewUseCase {
    suspend fun postReview(username: Username, postReviewRequest: PostReviewRequest): Review
}
