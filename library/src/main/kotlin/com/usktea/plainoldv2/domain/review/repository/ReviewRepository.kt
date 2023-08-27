package com.usktea.plainoldv2.domain.review.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.pageQuery
import com.linecorp.kotlinjdsl.spring.reactive.singleQueryOrNull
import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.domain.product.ProductStatus
import com.usktea.plainoldv2.domain.review.FindReviewSpec
import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.exception.ProductNotFoundException
import com.usktea.plainoldv2.exception.ReviewAlreadyWritten
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ReviewRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<Review> {
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

    override suspend fun findAll(): List<Review> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Review): Review {
        return entity.also {
            queryFactory.withFactory { factory ->
                factory.singleQueryOrNull<Product> {
                    select(entity(Product::class))
                    from(entity(Product::class))
                    whereAnd(
                        col(Product::id).equal(entity.productId),
                        col(Product::productStatus).equal(ProductStatus.ON_SALE)
                    )
                } ?: throw ProductNotFoundException()

                val found = factory.singleQueryOrNull<Review> {
                    select(entity(Review::class))
                    from(entity(Review::class))
                    whereAnd(
                        col(Review::orderNumber).equal(entity.orderNumber),
                        col(Review::productId).equal(entity.productId)
                    )
                }

                if (found != null) {
                    throw ReviewAlreadyWritten()
                }

                sessionFactory.withSession { session ->
                    session.persist(entity).flatMap { session.flush() }
                }.awaitSuspending()
            }

            it
        }
    }
}
