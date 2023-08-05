package com.usktea.plainoldv2.domain.oAuth.application

data class OAuthServiceFactory(
    private val services: MutableMap<String, OAuthService>
) {
    operator fun get(provider: String): OAuthService? {
        return services[provider]
    }
}
