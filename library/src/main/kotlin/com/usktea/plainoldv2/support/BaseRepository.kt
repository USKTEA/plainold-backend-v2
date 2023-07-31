package com.usktea.plainoldv2.support

interface BaseRepository<T> {
    suspend fun findAll(): List<T>
    suspend fun save(entity: T): T
}
