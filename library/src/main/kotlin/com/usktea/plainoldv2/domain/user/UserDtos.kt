package com.usktea.plainoldv2.domain.user

data class LoginRequestDto(
    val username: String,
    val password: String,
)

data class LoginRequest(
    val username: Username,
    val password: Password
) {
    companion object {
        fun from(loginRequestDto: LoginRequestDto): LoginRequest {
            return LoginRequest(
                Username(loginRequestDto.username),
                Password(loginRequestDto.password)
            )
        }
    }
}

data class LoginResultDto(
    val accessToken: String
)

data class UserInformationDto(
    val username: String,
    val nickname: String,
    val purchaseAmount: Long,
    val role: String
) {
    companion object {
        fun from(user: User): UserInformationDto {
            return UserInformationDto(
                username = user.username.value,
                nickname = user.password.value,
                purchaseAmount = user.purchaseAmount.amount,
                role = user.role.value
            )
        }
    }
}

data class CountUserDto(
    val count: Long
)

data class SignUpRequestDto(
    val username: String,
    val password: String,
    val nickname: String,
)

data class SignUpRequest(
    val username: Username,
    val password: Password,
    val nickname: Nickname
) {
    companion object {
        fun from(signUpRequestDto: SignUpRequestDto): SignUpRequest {
            return SignUpRequest(
                username = Username(signUpRequestDto.username),
                password = Password(signUpRequestDto.password),
                nickname = Nickname(signUpRequestDto.nickname)
            )
        }
    }
}

data class SignUpResult(
    val id: Long,
    val username: Username,
) {
    companion object {
        fun from(user: User): SignUpResult {
            return SignUpResult(id = user.id, username = user.username)
        }
    }
}

data class SignUpResultDto(
    val id: Long,
    val username: String
) {
    companion object {
        fun from(signUpResult: SignUpResult): SignUpResultDto {
            return SignUpResultDto(
                id = signUpResult.id,
                username = signUpResult.username.value,
            )
        }
    }
}

