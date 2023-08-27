package com.usktea.plainoldv2.domain.review.application

import com.usktea.plainoldv2.*
import com.usktea.plainoldv2.domain.cart.application.PASSWORD
import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import com.usktea.plainoldv2.domain.review.repository.ReviewRepository
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.test.context.ActiveProfiles

const val USERNAME = "tjrxo1234@gamil.com"
const val PRODUCT_ID = 1L
const val CATEGORY_ID = 1L
const val ORDER_NUMBER = "tjro1234-111111"
const val ONLY_PHOTO_REVIEWS = true
const val ALL_REVIEWS = false

@ActiveProfiles("test")
class ReviewServiceTest {
    private val userRepository = mockk<UserRepository>()
    private val productRepository = mockk<ProductRepository>()
    private val reviewRepository = mockk<ReviewRepository>()
    private val reviewService = ReviewService(reviewRepository, userRepository, productRepository)

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
        val product = createProduct(productId = PRODUCT_ID, categoryId = CATEGORY_ID)
        val postReviewRequest = createPostReviewRequest(orderNumber = ORDER_NUMBER, productId = PRODUCT_ID)
        val review = createReview(productId = PRODUCT_ID, orderNumber = ORDER_NUMBER)

        coEvery { userRepository.findByUsernameOrNull(username) } returns user
        coEvery { productRepository.findBySpec(any()) } returns product
        coEvery { reviewRepository.save(any()) } returns review

        val saved = reviewService.postReview(username = username, postReviewRequest = postReviewRequest)

        saved.productId shouldBe PRODUCT_ID
        saved.orderNumber shouldBe OrderNumber(ORDER_NUMBER)
    }
}
