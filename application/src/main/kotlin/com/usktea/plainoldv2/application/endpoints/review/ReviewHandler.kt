package com.usktea.plainoldv2.application.endpoints.review

import com.usktea.plainoldv2.domain.review.*
import com.usktea.plainoldv2.domain.review.application.ReviewService
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.ParameterNotFoundException
import com.usktea.plainoldv2.exception.RequestAttributeNotFoundException
import com.usktea.plainoldv2.exception.RequestBodyNotFoundException
import com.usktea.plainoldv2.support.PageDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

@Component
class ReviewHandler(
    private val reviewService: ReviewService
) {
    suspend fun getReviews(request: ServerRequest): ServerResponse {
        val productId = request.queryParamOrNull("productId")?.toLong() ?: throw ParameterNotFoundException()
        val photoReviews = request.queryParamOrNull("photoReviews")?.toBoolean() ?: throw ParameterNotFoundException()
        val pageNumber = request.queryParamOrNull("page")?.toInt() ?: throw ParameterNotFoundException()

        val pageable = PageRequest.of(pageNumber - 1, 10, Sort.by("id").descending())

        val found = reviewService.findAllByPaging(
            findReviewSpec = FindReviewSpec(productId = productId, photoReviews = photoReviews),
            pageable = pageable
        )
        val reviews = found.content.map { ReviewDto.from(it) }
        val page = PageDto(pageNumber, found.totalPages, found.totalElements)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(
            ReviewsDto(
                reviews = reviews,
                page = page
            )
        )
    }

    suspend fun postReview(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw ParameterNotFoundException()
        val postReviewRequest = request.awaitBodyOrNull<PostReviewRequestDto>()?.let {
            PostReviewRequest.from(it)
        } ?: throw RequestBodyNotFoundException()

        val review = reviewService.postReview(username = username, postReviewRequest = postReviewRequest)
        val uri = URI.create(review.id.toString())

        return created(uri).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(
            PostReviewResultDto(review.id)
        )
    }

    suspend fun editReview(request: ServerRequest): ServerResponse {
        val username = request.attributeOrNull("username") as? Username ?: throw RequestAttributeNotFoundException()
        val editReviewRequest = request.awaitBodyOrNull<EditReviewRequestDto>()?.let {
            EditReviewRequest.from(it)
        } ?: throw RequestBodyNotFoundException()

        val edited = reviewService.editReview(username = username, editReviewRequest = editReviewRequest)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(EditReviewResultDto(edited.id))
    }
}
