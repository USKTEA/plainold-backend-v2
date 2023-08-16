package com.usktea.plainoldv2.domain.option.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.fetch
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.usktea.plainoldv2.domain.option.OptionData
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.stereotype.Repository

@Repository
class OptionRepository(
    private val sessionFactory: Mutiny.SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<OptionData> {
    override suspend fun findAll(): List<OptionData> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: OptionData): OptionData {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    suspend fun findByProductIdOrNull(productId: Long): OptionData? {
        try {
            return queryFactory.singleQuery {
                select(entity(OptionData::class))
                from(entity(OptionData::class))
                fetch(OptionData::sizes)
                fetch(OptionData::colors)
                where(col(OptionData::productId).equal(productId))
            }
        } catch (exception: RuntimeException) {
            return null
        }
    }
}
