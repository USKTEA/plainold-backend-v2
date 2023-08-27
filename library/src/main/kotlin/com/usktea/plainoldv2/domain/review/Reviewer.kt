package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.domain.user.Nickname
import com.usktea.plainoldv2.domain.user.Username
import jakarta.persistence.Embeddable

@Embeddable
data class Reviewer(
    val username: Username,
    val nickname: Nickname
)
