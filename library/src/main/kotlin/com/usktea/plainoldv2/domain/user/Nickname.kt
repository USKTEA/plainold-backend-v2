package com.usktea.plainoldv2.domain.user

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Nickname(
    @Column(name = "nickname")
    val value: String
)
