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
