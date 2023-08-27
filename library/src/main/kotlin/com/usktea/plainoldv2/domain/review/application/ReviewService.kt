package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import com.usktea.plainoldv2.domain.review.FindReviewSpec
import com.usktea.plainoldv2.domain.review.PostReviewRequest
import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.domain.review.repository.ReviewRepository
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.exception.ProductNotFoundException
import com.usktea.plainoldv2.exception.UserNotExistsException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ReviewService(
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val productRepository: ProductRepository
) : FindAllReviewByPagingUseCase, PostReviewUseCase {
    override suspend fun findAllByPaging(findReviewSpec: FindReviewSpec, pageable: Pageable): Page<Review> {
        return reviewRepository.findAllByPaging(findReviewSpec, pageable)
    }

    override suspend fun postReview(username: Username, postReviewRequest: PostReviewRequest): Review {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UserNotExistsException()

        return Review.of(
            postReviewRequest = postReviewRequest,
            username = user.username,
            nickname = user.nickname
        ).also { reviewRepository.save(it) }
    }
}
