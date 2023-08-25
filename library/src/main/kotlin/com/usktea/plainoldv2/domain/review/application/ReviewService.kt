package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.domain.review.FindReviewSpec
import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.domain.review.repository.ReviewRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository
): FindAllReviewByPagingUseCase {
    override suspend fun findAllByPaging(findReviewSpec: FindReviewSpec, pageable: Pageable): Page<Review> {
        return reviewRepository.findAllByPaging(findReviewSpec, pageable)
    }
}
