package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.domain.review.FindReviewSpec
import com.usktea.plainoldv2.domain.review.Review
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FindAllReviewByPagingUseCase {
    suspend fun findAllByPaging(findReviewSpec: FindReviewSpec, pageable: Pageable): Page<Review>
}
