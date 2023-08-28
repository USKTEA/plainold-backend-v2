package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.createEditReviewRequest
import com.usktea.plainoldv2.createReview
import com.usktea.plainoldv2.createUsername
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

const val USERNAME = "tjrxo1234@gmail.com"
const val OTHER_USERNAME = "other1234@gamil.com"

class ReviewTest {
    @Test
    fun `Reviewer username과 동일한 username의 구매평 수정 요청이 온다면 구매평은 수정된다`() {
        val username = createUsername(USERNAME)
        val review = createReview(username = USERNAME)
        val comment = Comment("너무너무 좋아요")
        val rate = Rate(4)
        val imageUrl = ImageUrl("2")
        val editReviewRequest = createEditReviewRequest(comment = comment, rate = rate, imageUrl = imageUrl)

        review.comment shouldNotBe comment
        review.rate shouldNotBe rate
        review.imageUrl shouldNotBe imageUrl

        review.edit(username, editReviewRequest)

        review.comment shouldBe comment
        review.rate shouldBe rate
        review.imageUrl shouldBe imageUrl
    }

    @Test
    fun `Reviewer username과 동일하지 않은 username의 구매평 수정 요청이 온다면 예외가 발생한다`() {
        val username = createUsername(USERNAME)
        val review = createReview(username = OTHER_USERNAME)
        val comment = Comment("너무너무 좋아요")
        val rate = Rate(4)
        val imageUrl = ImageUrl("2")
        val editReviewRequest = createEditReviewRequest(comment = comment, rate = rate, imageUrl = imageUrl)

        shouldThrowAny { review.edit(username, editReviewRequest) }

        review.comment shouldNotBe comment
        review.rate shouldNotBe rate
        review.imageUrl shouldNotBe imageUrl
    }
}
