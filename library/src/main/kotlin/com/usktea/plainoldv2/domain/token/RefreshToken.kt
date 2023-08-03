package com.usktea.plainoldv2.domain.token

import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity

@Entity
class RefreshToken(
    id: Long = 0L,

    @Embedded
    val username: Username,
    val number: String
) : BaseEntity(id)
