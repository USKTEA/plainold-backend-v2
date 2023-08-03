package com.usktea.plainoldv2.domain.user

import com.usktea.plainoldv2.exception.UnIdentifiedUserException
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
class User(
    id: Long = 0L,

    @Embedded
    var username: Username,

    @Embedded
    var password: Password,

    @Embedded
    var nickname: Nickname,

    @Enumerated(EnumType.STRING)
    var role: Role,

    @Enumerated(EnumType.STRING)
    var userStatus: UserStatus,

    @Embedded
    @AttributeOverride(name = "amount", column = Column(name = "purchaseAmount"))
    var money: Money = Money(0L)
) : BaseEntity(id) {
    companion object {
        fun fake(): User {
            return User(
                username = Username("tjrxo1234@gmail.com"),
                password = Password("Password1234!"),
                nickname = Nickname("김뚜루"),
                role = Role.MEMBER,
                userStatus = UserStatus.ACTIVE,
            )
        }
    }

    fun authenticate(password: Password, passwordEncoder: PasswordEncoder) {
        identify(passwordEncoder.matches(password.value, this.password.value))
    }

    fun changePassword(password: Password, passwordEncoder: PasswordEncoder) {
        this.password = Password(passwordEncoder.encode(password.value))
    }

    private fun identify(value: Boolean) {
        if (!value) {
            throw UnIdentifiedUserException()
        }
    }
}
