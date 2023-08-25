package com.usktea.plainoldv2.application.endpoints.review

import com.usktea.plainoldv2.domain.review.FindReviewSpec
import com.usktea.plainoldv2.domain.review.ReviewDto
import com.usktea.plainoldv2.domain.review.ReviewsDto
import com.usktea.plainoldv2.domain.review.application.ReviewService
import com.usktea.plainoldv2.exception.ParameterNotFoundException
import com.usktea.plainoldv2.support.PageDto
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.queryParamOrNull

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
}
