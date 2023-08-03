package com.usktea.plainoldv2

import com.usktea.plainoldv2.domain.token.RefreshToken
import com.usktea.plainoldv2.domain.user.Username

fun createRefreshToken(username: String): RefreshToken {
    return RefreshToken(username = Username(username), number = "a.a.a")
}
