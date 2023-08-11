package com.usktea.plainoldv2.domain.token.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.usktea.plainoldv2.domain.token.RefreshToken
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<RefreshToken> {
    override suspend fun findAll(): List<RefreshToken> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: RefreshToken): RefreshToken {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(entity).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }
}
