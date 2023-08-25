package com.usktea.plainoldv2.domain.review

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Reviewer(
    val username: Username,
    val nickname: Nickname
)

@Embeddable
data class Username(
    @Column(name = "username")
    val value: String
)

@Embeddable
data class Nickname(
    @Column(name = "nickname")
    val value: String
)
