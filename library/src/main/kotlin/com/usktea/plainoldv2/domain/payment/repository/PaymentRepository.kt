package com.usktea.plainoldv2.domain.payment.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.usktea.plainoldv2.domain.payment.Payment
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class PaymentRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
): BaseRepository<Payment> {
    override suspend fun findAll(): List<Payment> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Payment): Payment {
        return entity.also {
            sessionFactory.withSession { session ->
                session.persist(entity).flatMap { session.flush() }
            }.awaitSuspending()
        }
    }
}
