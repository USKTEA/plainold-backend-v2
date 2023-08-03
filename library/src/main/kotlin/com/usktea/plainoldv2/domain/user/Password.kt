package com.usktea.plainoldv2.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Password(
    @Column(name = "password")
    val value: String
)
