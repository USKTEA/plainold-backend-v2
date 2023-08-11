package com.usktea.plainoldv2.domain.order.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.usktea.plainoldv2.domain.order.Order
import com.usktea.plainoldv2.domain.order.OrderLine
import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.domain.product.ProductStatus
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
}
