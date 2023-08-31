package com.usktea.plainoldv2.domain.user

import com.usktea.plainoldv2.exception.InvalidUsernameException
import com.usktea.plainoldv2.utils.RegExpProvider
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Username(
    @Column(name = "username")
    val value: String
) {

    init {
        if (!value.matches(RegExpProvider.username())) {
            throw InvalidUsernameException()
        }
    }

    fun beforeAt(): String {
        return value.split("@")[0]
    }
}
