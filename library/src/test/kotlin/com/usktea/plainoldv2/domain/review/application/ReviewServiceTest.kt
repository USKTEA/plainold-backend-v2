package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.*
import com.usktea.plainoldv2.domain.cart.application.PASSWORD
import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.review.ReviewStatus
import com.usktea.plainoldv2.domain.review.repository.ReviewRepository
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles

const val USERNAME = "tjrxo1234@gamil.com"
const val PRODUCT_ID = 1L
const val ORDER_NUMBER = "tjro1234-111111"
const val ONLY_PHOTO_REVIEWS = true
const val ALL_REVIEWS = false
const val REVIEW_ID = 1L

@ActiveProfiles("test")
class ReviewServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val reviewRepository = mockk<ReviewRepository>()
    private val reviewService = ReviewService(reviewRepository, userRepository)

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

    @Test
    fun `사용자 요청에 맞게 구매평을 생성한다`() = runTest {
        val username = createUsername(USERNAME)
        val password = createPassword(PASSWORD)
        val user = createUser(username = username, password = password)
        val postReviewRequest = createPostReviewRequest(orderNumber = ORDER_NUMBER, productId = PRODUCT_ID)
        val review = createReview(productId = PRODUCT_ID, orderNumber = ORDER_NUMBER)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { reviewRepository.save(any()) } returns review

        val saved = reviewService.postReview(username = username, postReviewRequest = postReviewRequest)

        saved.productId shouldBe PRODUCT_ID
        saved.orderNumber shouldBe OrderNumber(ORDER_NUMBER)
    }

    @Test
    fun `사용자 요청에 맞게 구매평을 수정한다`() = runTest {
        val username = createUsername(USERNAME)
        val password = createPassword(PASSWORD)
        val editReviewRequest = createEditReviewRequest(REVIEW_ID)
        val review =
            createReview(reviewId = REVIEW_ID, productId = PRODUCT_ID, orderNumber = ORDER_NUMBER, username = USERNAME)

        val user = createUser(username = username, password = password)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { reviewRepository.findByIdOrNull(REVIEW_ID) } returns review
        coEvery { reviewRepository.update(review) } just Runs

        val edited = reviewService.editReview(username = username, editReviewRequest = editReviewRequest)

        coVerify(exactly = 1) { reviewRepository.update(review) }
        edited.id shouldBe review.id
    }

    @Test
    fun `사용자 요청에 맞게 구매평을 삭제한다`() = runTest {
        val username = createUsername(USERNAME)
        val password = createPassword(PASSWORD)
        val user = createUser(username, password)
        val review =
            createReview(reviewId = REVIEW_ID, productId = PRODUCT_ID, orderNumber = ORDER_NUMBER, username = USERNAME)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { reviewRepository.findByIdOrNull(REVIEW_ID) } returns review
        coEvery { reviewRepository.update(review) } just Runs

        val deleted = reviewService.deleteReview(username, REVIEW_ID)

        coVerify(exactly = 1) { reviewRepository.update(review) }
        deleted.status shouldBe ReviewStatus.DELETED
    }
}
