package com.usktea.plainoldv2.domain.oAuth

data class RedirectUrlDto(
    val redirectUrl: String
)

data class OAuthLoginRequestDto(
    val code: String,
    val provider: String
)

data class UserProfile(
    val email: String,
    val nickname: String
)

data class KakaoTokenResponse(
    val token_type: String,
    val access_token:String,
    val expires_in: Int,
    val refresh_token: String,
    val refresh_token_expires_in: Int
)

data class NaverTokenResponse(
    val access_token: String,
    val refresh_token: String,
    val token_type: String,
    val expires_in: Int,
    val error: String?,
    val error_description: String?
)
