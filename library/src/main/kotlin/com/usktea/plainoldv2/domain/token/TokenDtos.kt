package com.usktea.plainoldv2.domain.token

data class TokenDto(
    val accessToken: String,
    val refreshToken: String
)

data class ReissueTokenResultDto(
    val accessToken: String
)
