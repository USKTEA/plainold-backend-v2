package com.usktea.plainoldv2.domain.user.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository
import kotlin.reflect.jvm.javaField

@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<User> {
    override suspend fun findAll(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: User): User {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    suspend fun findByUsernameOrNull(username: Username): User? {
        try {
            return queryFactory.singleQuery<User> {
                select(entity(User::class))
                from(entity(User::class))
                where(col(User::username).equal(username))
            }
        } catch (exception: Exception) {
            return null
        }
    }
}
