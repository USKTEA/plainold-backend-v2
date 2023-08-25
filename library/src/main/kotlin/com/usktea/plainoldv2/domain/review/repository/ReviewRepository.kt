package com.usktea.plainoldv2.domain.review.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.pageQuery
import com.usktea.plainoldv2.domain.review.FindReviewSpec
import com.usktea.plainoldv2.domain.review.Review
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ReviewRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) {
    suspend fun findAllByPaging(findReviewSpec: FindReviewSpec, pageable: Pageable): Page<Review> {
        return queryFactory.pageQuery(pageable) {
            select(entity(Review::class))
            from(entity(Review::class))
            where(findSpec(findReviewSpec))
        }
    }

    private fun WhereDsl.findSpec(spec: FindReviewSpec) =
        and(
            col(Review::productId).equal(spec.productId),
            spec.photoReviews.takeIf { it }?.let { col(Review::imageUrl).isNotNull() }
        )
}
