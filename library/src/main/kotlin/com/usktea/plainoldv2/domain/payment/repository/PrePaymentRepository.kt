package com.usktea.plainoldv2.domain.payment.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.usktea.plainoldv2.domain.payment.PrePayment
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class PrePaymentRepository(
    val sessionFactory: SessionFactory,
    val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<PrePayment> {
    override suspend fun findAll(): List<PrePayment> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: PrePayment): PrePayment {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }
}
