package com.usktea.plainoldv2.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "naver")
data class NaverOAuthProperties(
    val clientId: String,
    val clientSecret: String,
    val responseType: String,
    val redirectUri: String,
    val authorizationUri: String,
    val tokenUri: String,
    val userInformationUri: String,
    val state: String,
    val grantType: String,
    val provider: String
)
