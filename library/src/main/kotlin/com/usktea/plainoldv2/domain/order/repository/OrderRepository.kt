package com.usktea.plainoldv2.domain.order.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.querydsl.from.join
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.linecorp.kotlinjdsl.spring.reactive.listQuery
import com.usktea.plainoldv2.domain.order.Order
import com.usktea.plainoldv2.domain.order.OrderLine
import com.usktea.plainoldv2.domain.order.OrderNumber
import com.usktea.plainoldv2.domain.order.OrderStatus
import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.domain.product.ProductStatus
import com.usktea.plainoldv2.domain.review.Review
import com.usktea.plainoldv2.domain.review.ReviewStatus
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.InvalidOrderItemException
import com.usktea.plainoldv2.exception.UserNotExistsException
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class OrderRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<Order> {
    override suspend fun findAll(): List<Order> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Order): Order {
        val userCount = countUser(entity.username)
        val productIds = entity.orderLines.fold(mutableSetOf()) { productIds: MutableSet<Long>, orderLine: OrderLine ->
            productIds.add(orderLine.productId)
            productIds
        }
        val productCount = countProducts(productIds)

        if (userCount == 0L) {
            throw UserNotExistsException()
        }

        if (productCount != productIds.size.toLong()) {
            throw InvalidOrderItemException()
        }

        return entity.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    private suspend fun countProducts(productIds: MutableSet<Long>): Long {
        return queryFactory.singleQuery<Long> {
            select(count(Product::id))
            from(entity(Product::class))
            where(col(Product::id).`in`(productIds))
                .also { col(Product::productStatus).equal(ProductStatus.ON_SALE) }
        }
    }

    suspend fun countUser(username: Username): Long {
        return queryFactory.singleQuery<Long> {
            select(count(User::id))
            from(entity(User::class))
            where(col(User::username).equal(username))
        }
    }

    suspend fun findOrderCanWriteReview(username: Username, productId: Long): OrderNumber? {
        return queryFactory.withFactory { factory ->
            val orderNumbers = factory.listQuery<Row> {
                selectMulti(col(Order::id), col(Order::orderNumber), col(OrderLine::productId))
                from(entity(Order::class))
                join(Order::orderLines)
                whereAnd(
                    col(Order::username).equal(username),
                    col(Order::status).equal(OrderStatus.DELIVERY_COMPLETED),
                    col(OrderLine::productId).equal(productId)
                )
                groupBy(col(Order::id), col(Order::orderNumber), col(OrderLine::productId))
            }.map { it.orderNumber }.toSet()

            if (orderNumbers.isEmpty()) {
                return@withFactory null
            }

            val reviewsOrderNumbers = factory.listQuery<Review> {
                select(entity(Review::class))
                from(entity(Review::class))
                whereAnd(
                    col(Review::productId).equal(productId),
                    col(Review::status).equal(ReviewStatus.ACTIVE),
                    col(Review::orderNumber).`in`(orderNumbers),
                )
            }.map { it.orderNumber }.toSet()

            (orderNumbers - reviewsOrderNumbers).firstOrNull()
        }
    }
}

data class Row(
    val id: Long,
    val orderNumber: OrderNumber,
    val productId: Long,
)
