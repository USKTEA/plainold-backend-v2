package com.usktea.plainoldv2.domain.token.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.linecorp.kotlinjdsl.spring.reactive.singleQuery
import com.usktea.plainoldv2.domain.token.RefreshToken
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import jakarta.persistence.NoResultException
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

    suspend fun findByNumberOrNull(refreshTokenNumber: String): RefreshToken? {
        try {
            return queryFactory.singleQuery {
                select(entity(RefreshToken::class))
                from(entity(RefreshToken::class))
                where(col(RefreshToken::number).equal(refreshTokenNumber))
            }
        } catch (exception: NoResultException) {
            return null
        }
    }

    override suspend fun save(entity: RefreshToken): RefreshToken {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(entity).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    suspend fun update(refreshToken: RefreshToken) {
        queryFactory.transactionWithFactory { factory ->
            val found = factory.singleQuery<RefreshToken> {
                select(entity(RefreshToken::class))
                from(entity(RefreshToken::class))
                where(col(RefreshToken::id).equal(refreshToken.id))
            }

            found.updateTo(refreshToken)

            sessionFactory.withSession {session ->
                session.merge(found).flatMap { session.flush() }
            }
        }
    }
}

private fun RefreshToken.updateTo(refreshToken: RefreshToken) {
    this.number = refreshToken.number
}
