package com.usktea.plainoldv2.support

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@MappedSuperclass
class BaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L
) {
    @CreationTimestamp
    var createdAt: Instant = Instant.now()

    @UpdateTimestamp
    lateinit var updatedAt: Instant

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (javaClass != other?.javaClass) {
            return false
        }

        other as BaseEntity

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    fun createdAt(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val koreaZonedDateTime = this.createdAt.atZone(ZoneId.of("Asia/Seoul"))

        return formatter.format(koreaZonedDateTime)
    }
}
