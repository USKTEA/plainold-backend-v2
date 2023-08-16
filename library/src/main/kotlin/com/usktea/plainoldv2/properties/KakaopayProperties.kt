package com.usktea.plainoldv2.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao")
data class KakaopayProperties(
    val cid: String,
    val adminKey: String,
    val readyUri: String,
    val approvalUrl: String,
    val cancelUrl: String,
    val failUrl: String,
    val texFreeAmount: String,
    val paymentProvider: String,
    val approveUri: String
)
