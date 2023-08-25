package com.usktea.plainoldv2.domain.token

import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.support.BaseEntity
import com.usktea.plainoldv2.utils.JwtUtil
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import java.util.*

@Entity
class RefreshToken(
    id: Long = 0L,

    @Embedded
    val username: Username,
    var number: String
) : BaseEntity(id) {
    fun toNextVersion(jwtUtil: JwtUtil): Pair<String, String> {
        val accessToken = jwtUtil.encode(username.value)
        val refreshToken = jwtUtil.encode(UUID.randomUUID())
        number = refreshToken

        return Pair(accessToken, refreshToken)
    }
}
