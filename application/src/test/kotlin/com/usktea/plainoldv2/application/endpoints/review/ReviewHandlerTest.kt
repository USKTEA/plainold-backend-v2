package com.usktea.plainoldv2.application.endpoints.review

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.application.createPostReviewRequestDto
import com.usktea.plainoldv2.application.createReview
import com.usktea.plainoldv2.application.createUsername
import com.usktea.plainoldv2.domain.review.application.ReviewService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.data.domain.PageImpl
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.web.reactive.server.WebTestClient

const val USERNAME = "tjrxo1234@gmail.com"
const val PRODUCT_ID = 1L
const val TRUE = true
const val FALSE = false
const val PAGE = 1

@WebFluxTest(ReviewRouter::class, ReviewHandler::class)
class ReviewHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var reviewService: ReviewService

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @Test
    fun `구매평을 조회한다`() {
        val reviews = PageImpl(
            listOf(
                createReview(reviewId = 1L, productId = PRODUCT_ID),
                createReview(reviewId = 2L, productId = PRODUCT_ID),
                createReview(reviewId = 3L, productId = PRODUCT_ID),
            )
        )

        coEvery { reviewService.findAllByPaging(any(), any()) } returns reviews

        client.mutateWith(mockUser())
            .get()
            .uri("/reviews?productId=$PRODUCT_ID&photoReviews=$FALSE&page=$PAGE")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.reviews").exists()
    }

    @Test
    fun `구매평을 생성한다`() {
        val token = jwtUtil.encode(USERNAME)
        val postReviewRequestDto = createPostReviewRequestDto()
        val review = createReview(reviewId = 1L, productId = PRODUCT_ID)

        coEvery { reviewService.postReview(any(), any()) } returns review

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/reviews")
            .header("Authorization", "Bearer $token")
            .bodyValue(postReviewRequestDto)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.reviewId").isEqualTo(review.id)
    }
}
