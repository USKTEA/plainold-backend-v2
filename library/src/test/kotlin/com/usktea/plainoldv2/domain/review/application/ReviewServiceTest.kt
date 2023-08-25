package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.createFindReviewSpec
import com.usktea.plainoldv2.createReview
import com.usktea.plainoldv2.domain.review.repository.ReviewRepository
import io.kotest.matchers.collections.shouldHaveSize
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles

const val PRODUCT_ID = 1L
const val ONLY_PHOTO_REVIEWS = true
const val ALL_REVIEWS = false

@ActiveProfiles("test")
class ReviewServiceTest {
    private val reviewRepository = mockk<ReviewRepository>()
    private val reviewService = ReviewService(reviewRepository)

    @Test
    fun `페이징 조건에 맞는 구매평을 조회한다`() = runTest {
        val findReviewSpec = createFindReviewSpec(
            PRODUCT_ID, ALL_REVIEWS
        )
        val pageable = PageRequest.of(0, 10, Sort.by("id").descending())
        val review = createReview(productId = PRODUCT_ID)
        val reviews = PageImpl(listOf(review))

        coEvery { reviewRepository.findAllByPaging(findReviewSpec, pageable) } returns reviews

        val founds = reviewService.findAllByPaging(
            findReviewSpec = findReviewSpec, pageable = pageable
        )

        founds shouldHaveSize 1
    }
}
