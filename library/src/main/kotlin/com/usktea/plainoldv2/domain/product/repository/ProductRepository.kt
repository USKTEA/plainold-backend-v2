package com.usktea.plainoldv2.domain.product.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.where.WhereDsl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.listQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.pageQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import com.usktea.plainoldv2.domain.category.Category
import com.usktea.plainoldv2.domain.product.FindProductSpec
import com.usktea.plainoldv2.domain.product.Product
import com.usktea.plainoldv2.support.BaseRepository
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class ProductRepository(
    private val sessionFactory: Mutiny.SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
) : BaseRepository<Product> {
    override suspend fun findAll(): List<Product> {
        return queryFactory.listQuery<Product> {
            select(entity(Product::class))
            from(entity(Product::class))
            orderBy(col(Category::id).asc())
        }
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: Product): Product {
        return entity.also {
            sessionFactory.withSession { session -> session.persist(it).flatMap { session.flush() } }
                .awaitSuspending()
        }
    }

    suspend fun findAllByPaging(spec: FindProductSpec, pageable: Pageable): Page<Product> {
        return queryFactory.pageQuery(pageable) {
            select(entity(Product::class))
            from(entity(Product::class))
            where(findSpec(spec))
        }
    }

    suspend fun findBySpec(spec: FindProductSpec): Product {
        return queryFactory.singleQuery<Product> {
            select(entity(Product::class))
            from(entity(Product::class))
            where(findSpec(spec))
        }
    }

    private fun WhereDsl.findSpec(spec: FindProductSpec) =
        and(
            spec.categoryId?.let { col(Product::categoryId).equal(spec.categoryId) },
            spec.productId?.let { col(Product::id).equal(spec.productId) }
        )
}
