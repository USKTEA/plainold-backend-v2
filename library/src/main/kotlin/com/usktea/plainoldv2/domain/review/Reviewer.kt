package com.usktea.plainoldv2.domain.review

import com.usktea.plainoldv2.domain.user.Nickname
import com.usktea.plainoldv2.domain.user.Username
import jakarta.persistence.Embeddable

@Embeddable
data class Reviewer(
    val username: Username,
    val nickname: Nickname
) {
    companion object {
        fun of(username: Username, nickname: Nickname): Reviewer {
            return Reviewer(
                username = username,
                nickname = nickname
            )
        }
    }
}
