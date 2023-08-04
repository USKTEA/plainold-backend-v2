package com.usktea.plainoldv2.domain.user.application

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.token.application.TokenService
import com.usktea.plainoldv2.domain.user.LoginRequest
import com.usktea.plainoldv2.domain.user.UserInformationDto
import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.exception.LoginFailedException
import com.usktea.plainoldv2.exception.UnIdentifiedUserException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserLoginUseCase, GetUserInformationUseCase {
    override suspend fun login(loginRequestDto: LoginRequest): TokenDto {
        try {
            val user = userRepository.findByUsernameOrNull(loginRequestDto.username) ?: throw UnIdentifiedUserException()

            user.authenticate(loginRequestDto.password, passwordEncoder)

            return tokenService.issueToken(user.username)
        } catch (exception: Exception) {
            throw LoginFailedException()
        }
    }

    override suspend fun getUserInformation(username: Username): UserInformationDto {
        val user = userRepository.findByUsernameOrNull(username) ?: throw UnIdentifiedUserException()

        return UserInformationDto.from(user)
    }
}
