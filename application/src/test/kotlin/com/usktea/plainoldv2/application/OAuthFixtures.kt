package com.usktea.plainoldv2.application

import com.usktea.plainoldv2.domain.oAuth.OAuthLoginRequestDto

fun createOAuthLoginRequestDto(
    code: String, provider: String
): OAuthLoginRequestDto {
    return OAuthLoginRequestDto(
        code = code,
        provider = provider
    )
}
