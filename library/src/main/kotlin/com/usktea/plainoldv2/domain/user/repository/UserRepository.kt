package com.usktea.plainoldv2.domain.user.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.expression.count
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.deleteQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQueryOrNull
import com.usktea.plainoldv2.domain.user.User
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.exception.UsernameAlreadyInUse
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class UserRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<User> {
    override suspend fun findAll(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        queryFactory.deleteQuery<User> {}
    }

    override suspend fun save(user: User): User {
        val count = count(user.username)

        if (count > 0) {
            throw UsernameAlreadyInUse()
        }

        return user.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    suspend fun findByUsernameOrNull(username: Username): User? {
        return queryFactory.singleQueryOrNull<User> {
            select(entity(User::class))
            from(entity(User::class))
            where(col(User::username).equal(username))
        }
    }

    suspend fun count(username: Username): Long {
        return queryFactory.singleQuery<Long> {
            select(count(User::id))
            from(entity(User::class))
            where(col(User::username).equal(username))
        }
    }
}
