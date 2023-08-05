package com.usktea.plainoldv2.domain.oAuth

enum class OauthAttributes(private val providerName: String) {
    KAKAO("kakao") {
        override fun of(attributes: Map<String, Any>): UserProfile {
            return UserProfile(
                email = getKakaoAccount(attributes)["email"] as String,
                nickname = getKakaoProfile(attributes)["nickname"] as String
            )
        }

        private fun getKakaoProfile(attributes: Map<String, Any>): Map<String, Any> {
            return getKakaoAccount(attributes)["profile"] as Map<String, Any>
        }

        private fun getKakaoAccount(attributes: Map<String, Any>): Map<String, Any> {
            return attributes["kakao_account"] as Map<String, Any>
        }
    },
    NAVER("naver") {
        override fun of(attributes: Map<String, Any>): UserProfile {
            return UserProfile(
                email = getNaverResponse(attributes)["email"] as String,
                nickname = getNaverResponse(attributes)["nickname"] as String
            )
        }

        private fun getNaverResponse(attributes: Map<String, Any>): Map<Any, Any> {
            return attributes["response"] as Map<Any, Any>
        }
    };

    companion object {
        fun extract(providerName: String, attributes: Map<String, Any>): UserProfile {
            return values().firstOrNull { it.providerName == providerName }
                ?.of(attributes) ?: throw IllegalArgumentException()
        }
    }

    abstract fun of(attributes: Map<String, Any>): UserProfile
}
