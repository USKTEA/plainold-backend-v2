package com.usktea.plainoldv2.utils

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class UtilConfiguration {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()
    }

    @Bean
    fun jwtUtil(): JwtUtil {
        return JwtUtil(secret)
    }
}

