package com.usktea.plainoldv2.domain.category.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.listQuery
import com.usktea.plainoldv2.domain.category.Category
import com.usktea.plainoldv2.support.BaseRepository
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.stereotype.Repository

@Repository
class CategoryRepository(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<Category> {
    override suspend fun findAll(): List<Category> {
        return queryFactory.listQuery {
            select(entity(Category::class))
            from(entity(Category::class))
            orderBy(col(Category::id).asc())
        }
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Category): Category {
        //TODO
        return entity
    }
}
