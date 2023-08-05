package com.usktea.plainoldv2.domain.user.application

import com.usktea.plainoldv2.domain.user.SignUpRequest
import com.usktea.plainoldv2.domain.user.SignUpResult

interface SignUpUseCase {
    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResult
}
