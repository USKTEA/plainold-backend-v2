package com.usktea.plainoldv2.domain.user.application

import com.usktea.plainoldv2.domain.user.Username

interface CountUserUseCase {
    suspend fun count(username: Username): Long
}
