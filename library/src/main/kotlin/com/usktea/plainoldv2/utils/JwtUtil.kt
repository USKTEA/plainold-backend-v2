package com.usktea.plainoldv2.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import java.util.*

class JwtUtil(
    @Value("\${jwt.secret}")
    private val secret: String
) {
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)

    companion object {
        const val ACCESS_TOKEN_VALIDATION_SECOND: Long = 1000L * 60 * 30;
        const val REFRESH_TOKEN_VALIDATION_SECOND: Long = 1000L * 60 * 60 * 24 * 14
    }

    fun encode(username: String): String {
        return JWT.create()
            .withClaim("username", username)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDATION_SECOND))
            .sign(algorithm)
    }

    fun encode(uuid: UUID): String {
        return JWT.create()
            .withClaim("uuid", uuid.toString())
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDATION_SECOND))
            .sign(algorithm)
    }

    fun decode(token: String): String {
        val verifier = JWT.require(algorithm).build()

        val decodedJwt = verifier.verify(token)

        return decodedJwt.getClaim("username").asString()
    }

    fun decodeRefreshToken(refreshTokenNumber: String) {
        val verifier = JWT.require(algorithm).build()
        verifier.verify(refreshTokenNumber)
    }
}
