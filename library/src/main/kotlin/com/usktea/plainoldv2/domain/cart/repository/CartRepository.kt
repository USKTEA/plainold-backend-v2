package com.usktea.plainoldv2.domain.cart.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.linecorp.kotlinjdsl.spring.reactive.singleQuery
import com.usktea.plainoldv2.domain.cart.Cart
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class CartRepository(
    private val sessionFactory: Mutiny.SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<Cart> {
    suspend fun findByUserIdOrNull(userId: Long): Cart? {
        try {
            return queryFactory.singleQuery<Cart> {
                select(entity(Cart::class))
                from(entity(Cart::class))
                fetch(Cart::cartItems)
                where(col(Cart::userId).equal(userId))
            }
        } catch (exception: Exception) {
            return null
        }
    }

    override suspend fun findAll(): List<Cart> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Cart): Cart {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    suspend fun update(cart: Cart) {
        return queryFactory.transactionWithFactory { queryFactory ->
            val found = queryFactory.singleQuery<Cart> {
                select(entity(Cart::class))
                fetch(Cart::cartItems)
                from(entity(Cart::class))
                where(col(Cart::id).equal(cart.id))
            }

            found.updateTo(cart)

            sessionFactory.withSession { session ->
                session.merge(found).flatMap { session.flush() }
            }
        }
    }
}

private fun Cart.updateTo(cart: Cart) {
    this.cartItems = cart.cartItems
}
