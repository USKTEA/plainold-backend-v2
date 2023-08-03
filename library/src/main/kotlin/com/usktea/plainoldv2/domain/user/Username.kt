package com.usktea.plainoldv2.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Username(
    @Column(name = "username")
    val value: String
)
