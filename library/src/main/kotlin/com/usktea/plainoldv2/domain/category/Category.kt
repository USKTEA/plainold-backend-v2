package com.usktea.plainoldv2.domain.category

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.Entity

@Entity
class Category(
    id: Long = 0L,
    val name: String
) : BaseEntity(id)
