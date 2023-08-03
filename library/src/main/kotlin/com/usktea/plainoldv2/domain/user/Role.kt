package com.usktea.plainoldv2.domain.user

enum class Role(
    val value: String
) {
    GUEST("ROLE_GUEST"), MEMBER("ROLE_MEMBER"), ADMIN("ROLE_ADMIN")
}
