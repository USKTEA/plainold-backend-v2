package com.usktea.plainoldv2.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao")
data class KakaoOAuthProperties(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val authorizationUri: String,
    val tokenUri: String,
    val userInformationUri: String,
    val grantType: String,
    val responseType: String,
    val provider: String
)
