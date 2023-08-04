package com.usktea.plainoldv2.domain.user.application

import com.usktea.plainoldv2.domain.user.UserInformationDto
import com.usktea.plainoldv2.domain.user.Username

interface GetUserInformationUseCase {
    suspend fun getUserInformation(username: Username): UserInformationDto
}
