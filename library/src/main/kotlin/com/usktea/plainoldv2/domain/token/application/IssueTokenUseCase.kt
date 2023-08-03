package com.usktea.plainoldv2.domain.token.application

import com.usktea.plainoldv2.domain.token.TokenDto
import com.usktea.plainoldv2.domain.user.Username

interface IssueTokenUseCase {
    suspend fun issueToken(username: Username): TokenDto
}
